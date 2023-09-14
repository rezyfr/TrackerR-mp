package dev.rezyfr.trackerr.presentation.screens.home.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.usecase.category.SyncCategoryUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.GetRecentTransactionUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.GetTransactionSummaryUseCase
import dev.rezyfr.trackerr.domain.usecase.wallet.GetWalletBalanceUseCase
import dev.rezyfr.trackerr.mainDispatcher
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {
    private val getRecentTransactionUseCase by inject<GetRecentTransactionUseCase>()
    private val getTransactionSummaryUseCase by inject<GetTransactionSummaryUseCase>()
    private val getWalletBalanceUseCase by inject<GetWalletBalanceUseCase>()
    private val syncCategoryUseCase by inject<SyncCategoryUseCase>()

    var hasSyncCategory = false

    fun create(): HomeStore = object : HomeStore,
        Store<HomeStore.Intent, HomeStore.State, HomeStore.Label> by storeFactory.create(
            name = HomeStore::class.simpleName,
            initialState = HomeStore.State(),
            bootstrapper = null,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private inner class ExecutorImpl :
        CoroutineExecutor<HomeStore.Intent, Unit, HomeStore.State, HomeStore.Result, HomeStore.Label>(
            mainDispatcher
        ) {

        init {
            getRecentTransaction()
            getTransactionSummary()
            getWalletBalance()
            syncCategory()
        }

        private fun syncCategory() {
            if (hasSyncCategory) return
            scope.launch {
                syncCategoryUseCase.execute(null).handleResult(
                    ifError = { ex ->
                        publish(HomeStore.Label.MessageReceived(ex.message))
                    },
                    ifSuccess = { res ->
                        hasSyncCategory = true
                    }
                )
            }
        }

        private fun getRecentTransaction() {
            scope.launch {
                dispatch(HomeStore.Result.GetRecentTransaction(UiResult.Loading))
                getRecentTransactionUseCase.execute(null).handleResult(
                    ifError = { ex ->
                        publish(HomeStore.Label.MessageReceived(ex.message))
                    },
                    ifSuccess = { res ->
                        dispatch(HomeStore.Result.GetRecentTransaction(UiResult.Success(res)))
                    }
                )
            }
        }

        private fun getTransactionSummary() {
            scope.launch {
                dispatch(HomeStore.Result.GetTransactionSummary(UiResult.Loading))
                getTransactionSummaryUseCase.execute(8).handleResult(
                    ifError = { ex ->
                        publish(HomeStore.Label.MessageReceived(ex.message))
                    },
                    ifSuccess = { res ->
                        dispatch(HomeStore.Result.GetTransactionSummary(UiResult.Success(res)))
                    }
                )
            }
        }

        private fun getWalletBalance() {
            scope.launch {
                dispatch(HomeStore.Result.GetWalletBalance(UiResult.Loading))
                getWalletBalanceUseCase.execute(null).handleResult(
                    ifError = { ex ->
                        publish(HomeStore.Label.MessageReceived(ex.message))
                    },
                    ifSuccess = { res ->
                        dispatch(HomeStore.Result.GetWalletBalance(UiResult.Success(res)))
                    }
                )
            }
        }

        override fun executeIntent(intent: HomeStore.Intent, getState: () -> HomeStore.State) =
            when (intent) {
                HomeStore.Intent.GetRecentTransaction -> getRecentTransaction()
                HomeStore.Intent.GetTransactionSummary -> getTransactionSummary()
                HomeStore.Intent.GetWalletBalance -> getWalletBalance()
            }
    }

    private object ReducerImpl : Reducer<HomeStore.State, HomeStore.Result> {
        override fun HomeStore.State.reduce(msg: HomeStore.Result): HomeStore.State =
            when (msg) {
                is HomeStore.Result.GetRecentTransaction -> copy(recentTransaction = msg.result)
                is HomeStore.Result.GetTransactionSummary -> copy(transactionSummary = msg.result)
                is HomeStore.Result.GetWalletBalance -> copy(accBalance = msg.result)
            }
    }

}
