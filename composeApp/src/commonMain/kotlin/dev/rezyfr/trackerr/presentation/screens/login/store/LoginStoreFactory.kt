package dev.rezyfr.trackerr.presentation.screens.login.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.usecase.user.LoginUseCase
import dev.rezyfr.trackerr.mainDispatcher
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginStoreFactory(
    private val storeFactory: StoreFactory,
) : KoinComponent {
    private val loginUseCase: LoginUseCase by inject()
    fun create(): LoginStore =
        object : LoginStore,
            Store<LoginStore.Intent, LoginStore.State, Unit> by storeFactory.create(
                name = LoginStore::class.simpleName,
                initialState = LoginStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private inner class ExecutorImpl :
        CoroutineExecutor<LoginStore.Intent, Unit, LoginStore.State, LoginStore.Result, Nothing>(
            mainDispatcher
        ) {

        override fun executeIntent(intent: LoginStore.Intent, getState: () -> LoginStore.State) {
            when (intent) {
                LoginStore.Intent.Login -> signIn(getState().email, getState().password)
                is LoginStore.Intent.OnEmailChange -> dispatch(
                    LoginStore.Result.OnEmailChange(
                        intent.email
                    )
                )

                is LoginStore.Intent.OnPasswordChange -> dispatch(
                    LoginStore.Result.OnPasswordChange(
                        intent.password
                    )
                )
            }
        }

        private fun signIn(email: String, password: String) {
            scope.launch {
                dispatch(LoginStore.Result.Login(UiResult.Loading))
                loginUseCase.execute(LoginUseCase.Params(email, password)).handleResult(
                    ifError = { ex ->
                        dispatch(LoginStore.Result.Login(UiResult.Error(ex)))
                    },
                    ifSuccess = { res ->
                        dispatch(LoginStore.Result.Login(UiResult.Success(res)))
                    }
                )
            }
        }
    }

    private object ReducerImpl : Reducer<LoginStore.State, LoginStore.Result> {
        override fun LoginStore.State.reduce(msg: LoginStore.Result): LoginStore.State =
            when (msg) {
                is LoginStore.Result.OnEmailChange -> copy(email = msg.email)
                is LoginStore.Result.Login -> copy(loginResult = msg.result)
                is LoginStore.Result.OnPasswordChange -> copy(password = msg.password)
            }
    }
}