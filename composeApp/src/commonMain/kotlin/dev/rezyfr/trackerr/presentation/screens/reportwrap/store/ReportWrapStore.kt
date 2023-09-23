package dev.rezyfr.trackerr.presentation.screens.reportwrap.store

import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.transaction.TransactionReportModel

interface ReportWrapStore: Store<ReportWrapStore.Intent, ReportWrapStore.State, Nothing> {

    sealed class Intent {
        object GetReport : Intent()
    }

    sealed class Result {
        data class GetReportResult(val report: UiResult<TransactionReportModel>) : Result()
    }

    data class State(
        val report: UiResult<TransactionReportModel> = UiResult.Uninitialized
    )
}