package dev.rezyfr.trackerr.presentation.screens.main.transaction.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.usecase.transaction.GetTransactionWithDateUseCase
import dev.rezyfr.trackerr.mainDispatcher
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
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

        var hasSync: Boolean = false

        override fun executeIntent(
            intent: TransactionStore.Intent,
            getState: () -> TransactionStore.State
        ) {
            when (intent) {
                is TransactionStore.Intent.Init -> {
                    getTransaction(
                        intent.month,
                        null,
                        null,
                        null
                    )
                }

                is TransactionStore.Intent.ApplyFilter -> {
                    getTransaction(
                        intent.month,
                        intent.categoryId,
                        intent.type,
                        intent.sort,
                        true
                    )
                }
            }
        }

        private fun getTransaction(
            month: Month,
            categoryIds: List<Int>?,
            type: CategoryType?,
            sort: String?,
            forceLoad: Boolean = false,
        ) {
            if (hasSync && !forceLoad) return
            scope.launch {
                getTransactionWithDateUseCase.executeFlow(
                    GetTransactionWithDateUseCase.Params(
                        sortOrder = sort,
                        categoryIds = categoryIds,
                        type = type,
                    )
                ).collectLatest {
                    dispatch(TransactionStore.Result.GetTransaction(it))
                }
                hasSync = true
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
