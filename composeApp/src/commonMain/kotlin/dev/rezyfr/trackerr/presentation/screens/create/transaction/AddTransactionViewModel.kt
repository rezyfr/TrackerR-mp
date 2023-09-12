package dev.rezyfr.trackerr.presentation.screens.create.transaction

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.domain.model.WalletModel
import dev.rezyfr.trackerr.domain.usecase.category.GetCategoriesUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.CreateTransactionUseCase
import dev.rezyfr.trackerr.domain.usecase.wallet.GetWalletsUseCase
import dev.rezyfr.trackerr.presentation.base.BaseScreenModel
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DateProperty
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DayOfMonth
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Year
import dev.rezyfr.trackerr.presentation.component.base.datepicker.calculateDayOfMonths
import dev.rezyfr.trackerr.presentation.component.base.datepicker.calculateMonths
import dev.rezyfr.trackerr.presentation.component.base.datepicker.calculateYears
import dev.rezyfr.trackerr.presentation.component.base.datepicker.toDayOfMonth
import dev.rezyfr.trackerr.presentation.component.base.datepicker.toMonth
import dev.rezyfr.trackerr.presentation.component.base.datepicker.toYear
import dev.rezyfr.trackerr.presentation.component.ui.BottomSheet
import dev.rezyfr.trackerr.presentation.component.util.getCurrentLdt
import dev.rezyfr.trackerr.utils.NumberUtils
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.number

class AddTransactionViewModel(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getWalletsUseCase: GetWalletsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseScreenModel() {

    private val _state = MutableStateFlow(AddTransactionState())
    private val _trxResult = MutableStateFlow(_state.value.transactionResult)
    private val _walletResult = MutableStateFlow(_state.value.walletResult)
    private val _categoryResult = MutableStateFlow(_state.value.categoryResult)

    init {
        getWallet()
        getCategories()
    }

    val state = combine(
        _state,
        _trxResult,
        _walletResult,
        _categoryResult
    ) { state, trx, wallet, category ->
        AddTransactionState(
            type = state.type,
            amount = state.amount,
            categoryId = state.categoryId,
            createdDate = state.createdDate,
            description = state.description,
            selectedDay = state.selectedDay,
            selectedMonth = state.selectedMonth,
            selectedYear = state.selectedYear,
            selectedWallet = state.selectedWallet,
            transactionResult = trx,
            walletResult = wallet,
            dateOptions = state.dateOptions,
            datePickerSheet = state.datePickerSheet,
            walletBottomSheet = state.walletBottomSheet,
            categoryResult = category,
            selectedCategory = state.selectedCategory
        )
    }.flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            AddTransactionState()
        )


    fun onChangeType(type: CategoryType) {
        _state.update { it.copy(type = type) }
    }

    fun onChangeAmount(amount: String) {
        _state.update { it.copy(amount = NumberUtils.getCleanString(amount)) }
    }

    fun onChangeCategory(categoryId: Int) {
        val selected = (_categoryResult.value as? UiResult.Success)?.data?.find { it.id == categoryId }
        _state.update { it.copy(
            selectedCategory = selected
        ) }
    }

    fun onChangeDayOfMonth(dom: Int) {
        Napier.d("onChangeDayOfMonth: $dom")
        _state.update {
            it.copy(selectedDay = it.dateOptions.first.find { it.value == dom }!!)
        }
    }

    fun onChangeMonth(month: Int) {
        Napier.d("onChangeMonth: $month")
        _state.update {
            it.copy(
                selectedMonth = it.dateOptions.second.find { it.value == month }!!
            )
        }
    }

    fun onChangeYear(year: Int) {
        Napier.d("onChangeYear: $year")
        _state.update { state ->
            state.dateOptions.third.find { it.value == year }?.let {
                state.copy(selectedYear = it)
            } ?: state
        }
    }

    fun onChangeDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun onChangeWallet(walletId: Int) {
        _state.update {
            it.copy(
                selectedWallet = (_walletResult.value as? UiResult.Success)?.data?.find { it.id == walletId }
            )
        }
    }

    fun onContinue() {
        val state = _state.value
        if (state.selectedWallet == null && state.selectedCategory == null) return
        viewModelScope.launch {
            createTransactionUseCase.executeFlow(
                CreateTransactionUseCase.Param(
                    amount = state.amount,
                    categoryId = state.selectedCategory!!.id,
                    createdDate = "",
                    description = state.description,
                    walletId = state.selectedWallet!!.id
                )
            ).collectLatest {
                _trxResult.value = it
            }
        }
    }

    private fun getWallet() {
        viewModelScope.launch {
            getWalletsUseCase.executeFlow(null).collectLatest {
                _walletResult.value = it
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.executeFlow(_state.value.type)
                .collectLatest {
                    it.handleResult(
                        ifError = {
                            Napier.e("getCategories: ${it.message}")
                        },
                        ifSuccess = { cat ->
                            _categoryResult.value = UiResult.Success(cat)
                        }
                    )
                }
        }
    }
}

data class AddTransactionState(
    val type: CategoryType = CategoryType.EXPENSE,
    val amount: Double = 0.0,
    val categoryId: Int = 0,
    val createdDate: LocalDateTime = getCurrentLdt(),
    val selectedDay: DateProperty = createdDate.toDayOfMonth(),
    val selectedMonth: DateProperty = createdDate.toMonth(),
    val selectedYear: DateProperty = createdDate.toYear(IntRange(2000, 2100)),
    val description: String = "",
    val selectedWallet: WalletModel? = null,
    val selectedCategory: CategoryModel? = null,
    val transactionResult: UiResult<TransactionModel> = UiResult.Uninitialized,
    val walletResult: UiResult<List<WalletModel>> = UiResult.Uninitialized,
    val dateOptions: Triple<List<DateProperty>, List<DateProperty>, List<DateProperty>> = Triple(
        calculateDayOfMonths(),
        calculateMonths(),
        calculateYears()
    ),
    val categoryResult: UiResult<List<CategoryModel>> = UiResult.Uninitialized,
    val datePickerSheet: BottomSheet = BottomSheet(),
    val walletBottomSheet: BottomSheet = BottomSheet(),
    val categoryBottomSheet: BottomSheet = BottomSheet(),
)