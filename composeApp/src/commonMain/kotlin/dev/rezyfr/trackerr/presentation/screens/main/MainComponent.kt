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
import dev.rezyfr.trackerr.presentation.screens.create.category.AddCategoryComponent
import dev.rezyfr.trackerr.presentation.screens.create.transaction.AddTransactionComponent
import dev.rezyfr.trackerr.presentation.screens.home.HomeComponent
import org.koin.core.component.KoinComponent

class MainComponent(
    componentContext: ComponentContext,
    private val home: (ComponentContext) -> HomeComponent,
    private val addTransaction: (ComponentContext, (AddTransactionComponent.Action) -> Unit) -> AddTransactionComponent,
    private val addCategory: (ComponentContext, (AddCategoryComponent.Action) -> Unit) -> AddCategoryComponent
) : ComponentContext by componentContext, KoinComponent {

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
        }
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

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            is Configuration.Home -> Tab.Home(
                homeComponent = home(componentContext)
            )

            Configuration.Transaction -> Tab.Transaction
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
        object Transaction : Tab(1)
    }

    sealed class Screen : Child {
        data class AddTransaction(val addTransactionComponent: AddTransactionComponent) : Child
        data class AddCategory(val addCategoryComponent: AddCategoryComponent) : Child
    }

    sealed interface Child

    sealed class Action {
        object NavigateToAddTransaction : Action()
    }
}