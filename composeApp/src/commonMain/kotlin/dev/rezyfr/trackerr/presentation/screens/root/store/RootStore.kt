package dev.rezyfr.trackerr.presentation.screens.root.store

import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult

interface RootStore : Store<Unit, RootStore.State, Unit> {
    sealed class Result {
        data class TokenResult (val result: UiResult<Boolean>) : Result()
    }

    data class State(
        val tokenResult: UiResult<Boolean> = UiResult.Uninitialized,
    )
}