package dev.rezyfr.trackerr.presentation.screens.register

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.presentation.screens.register.store.RegisterStore
import dev.rezyfr.trackerr.presentation.screens.register.store.RegisterStoreFactory
import kotlinx.coroutines.flow.StateFlow

class RegisterComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val action: (Action) -> Unit
) : ComponentContext by componentContext {
    private val registerStore = RegisterStoreFactory(
        storeFactory = storeFactory
    ).create()

    val state: StateFlow<RegisterStore.State> = registerStore.stateFlow

    fun onEvent(event: RegisterStore.Intent) {
        registerStore.accept(event)
    }

    fun onAction(action: Action) {
        action(action)
    }

    sealed class Action {
        object NavigateToLogin : Action()
        object NavigateBack : Action()
        object NavigateToOnboarding : Action()
    }
}