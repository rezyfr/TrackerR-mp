package dev.rezyfr.trackerr.presentation.screens.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.presentation.screens.login.store.LoginStore
import dev.rezyfr.trackerr.presentation.screens.login.store.LoginStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class LoginComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val action: (Action) -> Unit
) : ComponentContext by componentContext {

    private val loginStore = LoginStoreFactory(
        storeFactory = storeFactory
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<LoginStore.State> = loginStore.stateFlow

    fun onEvent(event: LoginStore.Intent) {
        loginStore.accept(event)
    }

    fun onAction(action: Action) {
        action(action)
    }

    sealed class Action {
        object NavigateToRegister : Action()
        object NavigateBack : Action()
        object NavigateToOnboarding : Action()
    }
}