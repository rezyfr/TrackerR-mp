package dev.rezyfr.trackerr.presentation.screens.start

import com.arkivanov.decompose.ComponentContext

class StartComponent(
    componentContext: ComponentContext,
    private val action: (Action) -> Unit
) : ComponentContext by componentContext {

    fun onAction(action: Action) {
        action(action)
    }

    sealed class Action {
        object NavigateToLogin : Action()
        object NavigateToRegister : Action()

    }
}