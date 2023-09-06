package dev.rezyfr.trackerr.presentation.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.domain.model.TransactionSummaryModel
import dev.rezyfr.trackerr.domain.usecase.GetRecentTransactionUseCase
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val getRecentTransactionUseCase: GetRecentTransactionUseCase
) : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + ioDispatcher
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        getRecentTransaction()
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
}

data class HomeState(
    val recentTransaction: UiResult<List<TransactionModel>> = UiResult.Uninitialized,
    val accBalance: UiResult<Long> = UiResult.Success(94000),//UiResult.Uninitialized,
    val transactionSummary: UiResult<TransactionSummaryModel> = UiResult.Success(TransactionSummaryModel(5000, 1200))
)