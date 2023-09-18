package dev.rezyfr.trackerr.presentation.screens.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.usecase.category.GetCategoriesUseCase
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.base.datepicker.calculateMonths
import dev.rezyfr.trackerr.presentation.component.base.datepicker.toMonth
import dev.rezyfr.trackerr.presentation.component.ui.BottomSheet
import dev.rezyfr.trackerr.presentation.component.util.getCurrentLdt
import dev.rezyfr.trackerr.presentation.screens.create.category.AddCategoryComponent
import dev.rezyfr.trackerr.presentation.screens.create.transaction.AddTransactionComponent
import dev.rezyfr.trackerr.presentation.screens.main.home.HomeComponent
import dev.rezyfr.trackerr.presentation.screens.main.transaction.TransactionComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainComponent(
    componentContext: ComponentContext,
    private val home: (ComponentContext) -> HomeComponent,
    private val transaction: (ComponentContext) -> TransactionComponent,
    private val addTransaction: (ComponentContext, (AddTransactionComponent.Action) -> Unit) -> AddTransactionComponent,
    private val addCategory: (ComponentContext, (AddCategoryComponent.Action) -> Unit) -> AddCategoryComponent
) : ComponentContext by componentContext, KoinComponent {

    private val getCategoriesUseCase: GetCategoriesUseCase by inject()

    init {
        CoroutineScope(Dispatchers.Main).launch {
            getCategoriesUseCase.executeFlow(null).collectLatest { result ->
                when (result) {
                    is UiResult.Success -> {
                        filterPickerState.update {
                            it.copy(categories = result.data.asSequence())
                        }
                    }
                    else -> {
                        // TODO: Handle error
                    }
                }
            }
        }
    }

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory
    ) : this (
        componentContext = componentContext,
        home = { context: ComponentContext ->
            HomeComponent(
                componentContext = context,
                storeFactory = storeFactory
            )
        },
        transaction = { context: ComponentContext ->
            TransactionComponent(
                componentContext = context,
                storeFactory = storeFactory
            )
        },
        addTransaction = { context, action ->
            AddTransactionComponent(
                componentContext = context,
                storeFactory = storeFactory,
                action = action
            )
        },
        addCategory = { context, action ->
            AddCategoryComponent(
                componentContext = context,
                storeFactory = storeFactory,
                action = action
            )
        },
    )

    private val navigation = StackNavigation<Configuration>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Home,
        handleBackButton = false,
        childFactory = ::createChild
    )
    val child: Value<ChildStack<*, Child>> get() = stack
    val activeTabIndex: Value<Int> = stack.map {
        if (it.active.instance is Tab) (it.active.instance as Tab).index
        else -1
    }

    val monthPickerState: MutableStateFlow<MonthPickerState> = MutableStateFlow(MonthPickerState())
    val filterPickerState: MutableStateFlow<FilterPickerState> = MutableStateFlow(FilterPickerState())

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            is Configuration.Home -> Tab.Home(
                homeComponent = home(componentContext)
            )

            Configuration.Transaction -> Tab.Transaction(
                transactionComponent = transaction(componentContext)
            )
            Configuration.AddTransaction -> Screen.AddTransaction(
                addTransactionComponent = addTransaction(componentContext, ::onAddTransactionAction)
            )
            Configuration.AddCategory -> Screen.AddCategory(
                addCategoryComponent = addCategory(componentContext, ::onAddCategoryAction)
            )
        }

    fun onTabSelected(index: Int) {
        navigation.bringToFront(
            when (index) {
                0 -> Configuration.Home
                1 -> Configuration.Transaction
                else -> error("Unknown tab index: $index")
            }
        )
    }

    fun onAction(action: Action) {
        when (action) {
            Action.NavigateToAddTransaction -> {
                navigation.push(Configuration.AddTransaction)
            }
        }
    }

    fun onEvent(intent: Intent) {
        when (intent) {
            is Intent.OnChangeMonth -> {
                monthPickerState.update {
                    it.copy(
                        selectedMonth = intent.month
                    )
                }
            }

            is Intent.OnClickMonthPicker -> {
                monthPickerState.value.monthPickerSheet.expand()
            }

            is Intent.OnSelectType -> {
                filterPickerState.update {
                    it.copy(
                        selectedType = intent.type
                    )
                }
            }

            is Intent.OnApplyFilter -> {
                filterPickerState.value.filterPickerSheet.collapse()
                filterPickerState.update { it.copy(appliedFilter = !it.appliedFilter) }
            }

            is Intent.OnResetFilter -> {
                filterPickerState.update {
                    it.copy(
                        selectedType = null,
                        selectedSortOrder = null,
                        selectedCategoryIds = sequenceOf()
                    )
                }
            }

            is Intent.OnSelectSort -> {
                filterPickerState.update {
                    it.copy(
                        selectedSortOrder = intent.sortOrder
                    )
                }
            }

            is Intent.OnSelectCategories -> {
                filterPickerState.value.categoryPickerSheet.collapse()
                filterPickerState.update {
                    it.copy(selectedCategoryIds = intent.categoryIds)
                }
            }
        }
    }

    private fun onAddTransactionAction(action: AddTransactionComponent.Action) {
        when (action) {
            AddTransactionComponent.Action.NavigateBack -> {
                navigation.pop()
            }
            AddTransactionComponent.Action.Finish -> {
                navigation.pop()
            }
            AddTransactionComponent.Action.NavigateToAddCategory -> {
                navigation.push(Configuration.AddCategory)
            }
        }
    }

    private fun onAddCategoryAction(action: AddCategoryComponent.Action) {
        when (action) {
            AddCategoryComponent.Action.NavigateBack -> {
                navigation.pop()
            }
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Home : Configuration()
        @Parcelize
        object Transaction : Configuration()
        @Parcelize
        object AddTransaction : Configuration()
        @Parcelize
        object AddCategory : Configuration()
    }

    sealed class Tab(val index: Int): Child {
        data class Home(val homeComponent: HomeComponent) : Tab(0)
        data class Transaction(val transactionComponent: TransactionComponent) : Tab(1)
    }

    sealed class Screen : Child {
        data class AddTransaction(val addTransactionComponent: AddTransactionComponent) : Child
        data class AddCategory(val addCategoryComponent: AddCategoryComponent) : Child
    }

    sealed interface Child

    sealed class Action {
        object NavigateToAddTransaction : Action()
    }

    sealed class Intent {
        data class OnChangeMonth(val month: Month) : Intent()
        object OnClickMonthPicker : Intent()
        data class OnSelectType(val type: CategoryType) : Intent()
        data class OnSelectCategories(val categoryIds: Sequence<Int>) : Intent()
        object OnResetFilter : Intent()
        data class OnSelectSort(val sortOrder: String) : Intent()
        object OnApplyFilter : Intent()
    }

    data class MonthPickerState(
        val monthPickerSheet: BottomSheet = BottomSheet(),
        val monthOptions: List<Month> = calculateMonths(),
        val selectedMonth: Month = getCurrentLdt().toMonth()
    )

    data class FilterPickerState(
        val filterPickerSheet: BottomSheet = BottomSheet(),
        val categoryPickerSheet: BottomSheet = BottomSheet(),
        val selectedType: CategoryType? = null,
        val appliedFilter: Boolean = false,
        val sortOrders: List<String> = listOf("Newest", "Oldest"),
        val selectedSortOrder: String? = null,
        val categories: Sequence<CategoryModel> = sequenceOf(),
        val selectedCategoryIds: Sequence<Int> = sequenceOf()
    )
}