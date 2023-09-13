package dev.rezyfr.trackerr.presentation.screens.create.account

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.presentation.screens.create.account.store.AddAccountStore
import dev.rezyfr.trackerr.presentation.screens.create.account.store.AddAccountStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class AddAccountComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val action: (Action) -> Unit
) : ComponentContext by componentContext {

    private val addAccountStore = AddAccountStoreFactory(
        storeFactory
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AddAccountStore.State> = addAccountStore.stateFlow

    fun onEvent(event: AddAccountStore.Intent) {
        addAccountStore.accept(event)
    }

    fun onAction(action: Action) {
        action(action)
    }

    sealed class Action {
        object NavigateBack : Action()
    }
}