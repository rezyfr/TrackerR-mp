package dev.rezyfr.trackerr.presentation.screens.onboarding

import com.arkivanov.decompose.ComponentContext

class OnboardingComponent(
    componentContext: ComponentContext,
    private val action: (Action) -> Unit
) : ComponentContext by componentContext {

    fun onAction(action: Action) {
        action(action)
    }

    sealed class Action {
        object NavigateToCreateAccount : Action()
    }
}