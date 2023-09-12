package dev.rezyfr.trackerr.presentation.screens.register.store

import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult

interface RegisterStore : Store<RegisterStore.Intent, RegisterStore.State, Unit> {
    sealed class Intent {
        data class OnEmailChange(val email: String) : Intent()
        data class OnPasswordChange(val password: String) : Intent()
        data class OnNameChange(val name: String) : Intent()
        object Register : Intent()
    }

    sealed class Result {
        data class OnEmailChange(val email: String) : Result()
        data class OnPasswordChange(val password: String) : Result()
        data class OnNameChange(val name: String) : Result()
        data class Register(val result: UiResult<Unit>) : Result()
    }

    data class State(
        val registerResult: UiResult<Unit> = UiResult.Uninitialized,
        val email: String = "",
        val password: String = "",
        val name: String = "",
    )
}