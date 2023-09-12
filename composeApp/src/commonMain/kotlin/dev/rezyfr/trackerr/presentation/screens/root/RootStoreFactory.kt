package dev.rezyfr.trackerr.presentation.screens.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.create
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
            Store<RootStore.Intent, Unit, Unit> by storeFactory.create(
                name = RootStore::class.simpleName,
                initialState = Unit,
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
            ) {}

    private inner class ExecutorImpl : CoroutineExecutor<RootStore.Intent, Unit, Unit, Unit, Unit>() {
        override fun executeIntent(intent: RootStore.Intent, getState: () -> Unit) {
            when (intent) {
                RootStore.Intent.CheckUserToken -> checkToken()
            }
        }

        private fun checkToken() {
            scope.launch {
                checkTokenUseCase.executeFlow(Unit).collectLatest { result ->
                    onTokenValid(result.isSuccess() && (result.asSuccess()?.data == true))
                }
            }
        }
    }
}