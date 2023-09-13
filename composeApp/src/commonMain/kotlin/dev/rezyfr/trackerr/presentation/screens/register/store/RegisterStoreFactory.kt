package dev.rezyfr.trackerr.presentation.screens.register.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.usecase.user.RegisterUseCase
import dev.rezyfr.trackerr.mainDispatcher
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegisterStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {
    private val registerUseCase: RegisterUseCase by inject()

    fun create() : RegisterStore = object : RegisterStore,
        Store<RegisterStore.Intent, RegisterStore.State, Unit> by storeFactory.create(
            name = RegisterStore::class.simpleName,
            initialState = RegisterStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private inner class ExecutorImpl : CoroutineExecutor<RegisterStore.Intent, Unit, RegisterStore.State, RegisterStore.Result, Nothing>(
        mainDispatcher
    ) {
        override fun executeIntent(intent: RegisterStore.Intent, getState: () -> RegisterStore.State) {
            when (intent) {
                RegisterStore.Intent.Register -> register(getState().email, getState().password, getState().name)
                is RegisterStore.Intent.OnEmailChange -> dispatch(RegisterStore.Result.OnEmailChange(intent.email))
                is RegisterStore.Intent.OnPasswordChange -> dispatch(RegisterStore.Result.OnPasswordChange(intent.password))
                is RegisterStore.Intent.OnNameChange -> dispatch(RegisterStore.Result.OnNameChange(intent.name))
            }
        }

        private fun register(email: String, password: String, name: String) {
            scope.launch {
                dispatch(RegisterStore.Result.Register(UiResult.Loading))
                registerUseCase.execute(RegisterUseCase.Params(email, password, name)).handleResult(
                    ifError = { ex ->
                        dispatch(RegisterStore.Result.Register(UiResult.Error(ex)))
                    },
                    ifSuccess = {
                        dispatch(RegisterStore.Result.Register(UiResult.Success(Unit)))
                    }
                )
            }
        }
    }

    private object ReducerImpl : Reducer<RegisterStore.State, RegisterStore.Result> {
        override fun RegisterStore.State.reduce(msg: RegisterStore.Result): RegisterStore.State =
            when (msg) {
                is RegisterStore.Result.OnEmailChange -> copy(email = msg.email)
                is RegisterStore.Result.OnNameChange -> copy(name = msg.name)
                is RegisterStore.Result.OnPasswordChange -> copy(password = msg.password)
                is RegisterStore.Result.Register -> copy(registerResult = msg.result)
            }
    }
}