package dev.rezyfr.trackerr.presentation.screens.main.transaction.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.usecase.transaction.GetTransactionWithDateUseCase
import dev.rezyfr.trackerr.mainDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TransactionStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val getTransactionWithDateUseCase: GetTransactionWithDateUseCase by inject()

    fun create(): TransactionStore = object : TransactionStore,
        Store<TransactionStore.Intent, TransactionStore.State, TransactionStore.Label> by storeFactory.create(
            name = TransactionStore::class.simpleName,
            initialState = TransactionStore.State(),
            bootstrapper = null,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private inner class ExecutorImpl :
        CoroutineExecutor<TransactionStore.Intent, Unit, TransactionStore.State, TransactionStore.Result, TransactionStore.Label>(
            mainDispatcher
        ) {
        override fun executeIntent(
            intent: TransactionStore.Intent,
            getState: () -> TransactionStore.State
        ) {
            when (intent) {
                is TransactionStore.Intent.Init -> {
                    getTransaction()
                }
            }
        }

        private fun getTransaction() {
            scope.launch {
                getTransactionWithDateUseCase.executeFlow(
                    GetTransactionWithDateUseCase.Params(
                        sortOrder = "DESC",
                        categoryId = null,
                        type = null
                    )
                ).collectLatest {
                    dispatch(TransactionStore.Result.GetTransaction(it))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<TransactionStore.State, TransactionStore.Result> {
        override fun TransactionStore.State.reduce(msg: TransactionStore.Result): TransactionStore.State {
            return when (msg) {
                is TransactionStore.Result.GetTransaction -> {
                    copy(transaction = msg.result)
                }
            }
        }
    }
}
