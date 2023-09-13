package dev.rezyfr.trackerr.presentation.screens.root.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.usecase.user.CheckTokenUseCase
import dev.rezyfr.trackerr.presentation.screens.login.store.LoginStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RootStoreFactory(
    private val storeFactory: StoreFactory,
    private val onTokenValid: (Boolean) -> Unit
) : KoinComponent {
    private val checkTokenUseCase: CheckTokenUseCase by inject()
    fun create(): RootStore =
        object : RootStore,
            Store<Unit, RootStore.State, Unit> by storeFactory.create(
                name = RootStore::class.simpleName,
                initialState = RootStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private inner class ExecutorImpl : CoroutineExecutor<Unit, Unit, RootStore.State, RootStore.Result, Unit>() {
        init {
            checkToken()
        }

        private fun checkToken() {
            scope.launch {
                checkTokenUseCase.executeFlow(Unit).collectLatest { result ->
                    dispatch(RootStore.Result.TokenResult(result))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<RootStore.State, RootStore.Result> {
        override fun RootStore.State.reduce(msg: RootStore.Result): RootStore.State =
            when (msg) {
                is RootStore.Result.TokenResult -> copy(tokenResult = msg.result)
            }
    }
}