package dev.rezyfr.trackerr.presentation.screens.create.transaction

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.domain.model.WalletModel
import dev.rezyfr.trackerr.domain.usecase.transaction.CreateTransactionUseCase
import dev.rezyfr.trackerr.domain.usecase.wallet.GetWalletsUseCase
import dev.rezyfr.trackerr.presentation.base.BaseScreenModel
import dev.rezyfr.trackerr.utils.NumberUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getWalletsUseCase: GetWalletsUseCase
) : BaseScreenModel() {

    private val _state = MutableStateFlow(AddTransactionState())
    private val _trxResult = MutableStateFlow(_state.value.transactionResult)
    private val _walletResult = MutableStateFlow(_state.value.walletResult)

    init {
        getWallet()
    }

    val state = combine(
        _state,
        _trxResult,
        _walletResult
    ) { state, trx, wallet ->
        AddTransactionState(
            type = state.type,
            amount = state.amount,
            categoryId = state.categoryId,
            createdDate = state.createdDate,
            description = state.description,
            walletId = state.walletId,
            transactionResult = trx,
            walletResult = wallet
        )
    }.flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            AddTransactionState()
        )


    fun onChangeType(type: String) {
        _state.update { it.copy(type = type) }
    }

    fun onChangeAmount(amount: String) {
        _state.update { it.copy(amount = NumberUtils.getCleanString(amount)) }
    }

    fun onChangeCategory(categoryId: Int) {
        _state.update { it.copy(categoryId = categoryId) }
    }

    fun onChangeDate(createdDate: String) {
        _state.update { it.copy(createdDate = createdDate) }
    }

    fun onChangeDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun onChangeWallet(walletId: Int) {
        _state.update { it.copy(walletId = walletId) }
    }

    fun onContinue() {
        viewModelScope.launch {
            val state = _state.value
            createTransactionUseCase.executeFlow(
                CreateTransactionUseCase.Param(
                    amount = state.amount,
                    categoryId = state.categoryId,
                    createdDate = state.createdDate,
                    description = state.description,
                    walletId = state.walletId
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
}

data class AddTransactionState(
    val type: String = "Expense",
    val amount: Double = 0.0,
    val categoryId: Int = 0,
    val createdDate: String = "",
    val description: String = "",
    val walletId: Int = 0,
    val transactionResult: UiResult<TransactionModel> = UiResult.Uninitialized,
    val walletResult: UiResult<List<WalletModel>> = UiResult.Uninitialized
)