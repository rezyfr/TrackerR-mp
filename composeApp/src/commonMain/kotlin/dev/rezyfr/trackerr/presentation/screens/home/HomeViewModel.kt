package dev.rezyfr.trackerr.presentation.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.domain.model.TransactionSummaryModel
import dev.rezyfr.trackerr.domain.usecase.category.SyncCategoryUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.GetRecentTransactionUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.GetTransactionSummaryUseCase
import dev.rezyfr.trackerr.domain.usecase.wallet.GetWalletBalanceUseCase
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val getRecentTransactionUseCase: GetRecentTransactionUseCase,
    private val getWalletBalanceUseCase: GetWalletBalanceUseCase,
    private val getTransactionSummaryUseCase: GetTransactionSummaryUseCase,
) : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + ioDispatcher
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        getRecentTransaction()
        getWalletBalance()
        getTransactionSummary()
    }

    private fun getWalletBalance() {
        viewModelScope.launch {
            getWalletBalanceUseCase.execute(null).handleResult(
                ifError = { ex ->
                    _state.update { it.copy(accBalance = UiResult.Error(ex)) }
                },
                ifSuccess = { result ->
                    _state.update { it.copy(accBalance = UiResult.Success(result)) }
                }
            )
        }
    }

    private fun getRecentTransaction() {
        viewModelScope.launch {
            getRecentTransactionUseCase.execute(null).handleResult(
                ifError = { ex ->
                    _state.update { it.copy(recentTransaction = UiResult.Error(ex)) }
                },
                ifSuccess = { result ->
                    _state.update { it.copy(recentTransaction = UiResult.Success(result)) }
                }
            )
        }
    }

    private fun getTransactionSummary() {
        viewModelScope.launch {
            getTransactionSummaryUseCase.execute(8).handleResult(
                ifError = { ex ->
                    _state.update { it.copy(transactionSummary = UiResult.Error(ex)) }
                },
                ifSuccess = { result ->
                    _state.update { it.copy(transactionSummary = UiResult.Success(result)) }
                }
            )
        }
    }
}

data class HomeState(
    val recentTransaction: UiResult<List<TransactionModel>> = UiResult.Uninitialized,
    val accBalance: UiResult<Long> = UiResult.Uninitialized,
    val transactionSummary: UiResult<TransactionSummaryModel> = UiResult.Uninitialized,
    val errorMessage: String = ""
)