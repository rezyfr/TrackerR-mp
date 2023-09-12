package dev.rezyfr.trackerr.presentation.screens.login.store

import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult

interface LoginStore : Store<LoginStore.Intent, LoginStore.State, Unit> {

    sealed class Intent {
        data class OnEmailChange(val email: String) : Intent()
        data class OnPasswordChange(val password: String) : Intent()
        object Login : Intent()
    }

    sealed class Result {
        data class OnEmailChange(val email: String) : Result()
        data class OnPasswordChange(val password: String) : Result()
        data class Login(val result: UiResult<Unit>) : Result()
    }

    data class State(
        val loginResult: UiResult<Unit> = UiResult.Uninitialized,
        val email: String = "",
        val password: String = "",
    )
}