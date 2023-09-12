package dev.rezyfr.trackerr.presentation.screens.home.store

import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.domain.model.TransactionSummaryModel

interface HomeStore: Store<HomeStore.Intent, HomeStore.State, HomeStore.Label> {
    sealed class Intent {
        object GetRecentTransaction : Intent()
        object GetWalletBalance : Intent()
        object GetTransactionSummary : Intent()
    }

    sealed class Result {
        data class GetRecentTransaction(val result: UiResult<List<TransactionModel>>) : Result()
        data class GetWalletBalance(val result: UiResult<Long>) : Result()
        data class GetTransactionSummary(val result: UiResult<TransactionSummaryModel>) : Result()
    }

    data class State(
        val recentTransaction: UiResult<List<TransactionModel>> = UiResult.Uninitialized,
        val accBalance: UiResult<Long> = UiResult.Uninitialized,
        val transactionSummary: UiResult<TransactionSummaryModel> = UiResult.Uninitialized,
    )

    sealed class Label {
        data class MessageReceived(val message: String?): Label()
    }
}