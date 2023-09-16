package dev.rezyfr.trackerr.presentation.screens.main.transaction.store

import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.transaction.TransactionModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionWithDateModel
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month

interface TransactionStore : Store<TransactionStore.Intent, TransactionStore.State, TransactionStore.Label> {
    sealed class Intent {
        data class Init(val month: Month) : Intent()
    }

    sealed class Result {
        data class GetTransaction(val result: UiResult<List<TransactionWithDateModel>>) : Result()
    }

    data class State(
        val transaction: UiResult<List<TransactionWithDateModel>> = UiResult.Uninitialized,
    )

    sealed class Label {
        data class MessageReceived(val message: String?): Label()
    }
}