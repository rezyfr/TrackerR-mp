package dev.rezyfr.trackerr.presentation.screens.reportwrap.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.usecase.transaction.GetTransactionReportUseCase
import dev.rezyfr.trackerr.mainDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ReportWrapStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {
    fun create(): ReportWrapStore = object : ReportWrapStore,
        Store<ReportWrapStore.Intent, ReportWrapStore.State, Nothing> by storeFactory.create(
            name = ReportWrapStore::class.simpleName,
            initialState = ReportWrapStore.State(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl()
        ) {}

    inner class ExecutorImpl :
        CoroutineExecutor<ReportWrapStore.Intent, Unit, ReportWrapStore.State, ReportWrapStore.Result, Nothing>(
            mainDispatcher
        ), KoinComponent {
        private val getTransactionReportUseCase by inject<GetTransactionReportUseCase>()

        init {
            getTransactionReport()
        }

        private fun getTransactionReport() {
            scope.launch {
                getTransactionReportUseCase.executeFlow(null).collectLatest {
                    dispatch(ReportWrapStore.Result.GetReportResult(it))
                }
            }
        }

        override fun executeIntent(
            intent: ReportWrapStore.Intent,
            getState: () -> ReportWrapStore.State
        ) {
            when(intent) {
                ReportWrapStore.Intent.GetReport -> getTransactionReport()
            }
        }
    }

    inner class ReducerImpl : Reducer<ReportWrapStore.State, ReportWrapStore.Result> {
        override fun ReportWrapStore.State.reduce(msg: ReportWrapStore.Result): ReportWrapStore.State {
            return when (msg) {
                is ReportWrapStore.Result.GetReportResult -> copy(report = msg.report)
            }
        }
    }
}