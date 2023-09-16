package dev.rezyfr.trackerr.presentation.screens.home.store

import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.Granularity
import dev.rezyfr.trackerr.domain.model.transaction.TransactionFrequencyModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionSummaryModel
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DateProperty
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.base.datepicker.calculateMonths
import dev.rezyfr.trackerr.presentation.component.base.datepicker.toMonth
import dev.rezyfr.trackerr.presentation.component.ui.BottomSheet
import dev.rezyfr.trackerr.presentation.component.util.getCurrentLdt

interface HomeStore: Store<HomeStore.Intent, HomeStore.State, HomeStore.Label> {
    sealed class Intent {
        object Init : Intent()
        object GetRecentTransaction : Intent()
        object GetWalletBalance : Intent()
        data class GetTransactionSummary(val month: Int) : Intent()
        data class GetTransactionFrequency(val granularity: Granularity) : Intent()
        data class OnChangeGranularity(val granularity: Granularity) : Intent()
        data class OnChangeMonth(val month: Month) : Intent()
    }

    sealed class Result {
        data class GetRecentTransaction(val result: UiResult<List<TransactionModel>>) : Result()
        data class GetWalletBalance(val result: UiResult<Long>) : Result()
        data class GetTransactionSummary(val result: UiResult<TransactionSummaryModel>) : Result()
        data class GetTransactionFrequency(val result: UiResult<TransactionFrequencyModel>) : Result()
        data class OnChangeGranularity(val granularity: Granularity) : Result()
        data class OnChangeMonth(val month: Month) : Result()
    }

    data class State(
        val recentTransaction: UiResult<List<TransactionModel>> = UiResult.Uninitialized,
        val accBalance: UiResult<Long> = UiResult.Uninitialized,
        val transactionSummary: UiResult<TransactionSummaryModel> = UiResult.Uninitialized,
        val transactionFrequency : UiResult<TransactionFrequencyModel> = UiResult.Uninitialized,
        val selectedGranularity: Granularity = Granularity.WEEK,
        val selectedMonth: Month = getCurrentLdt().toMonth(),
        val monthPickerSheet: BottomSheet = BottomSheet(),
        val monthOptions: List<Month> = calculateMonths()
    )

    sealed class Label {
        data class MessageReceived(val message: String?): Label()
    }
}