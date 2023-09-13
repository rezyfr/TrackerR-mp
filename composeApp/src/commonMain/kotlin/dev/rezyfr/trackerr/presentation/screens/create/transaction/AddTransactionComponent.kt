package dev.rezyfr.trackerr.presentation.screens.create.transaction

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.presentation.screens.create.transaction.store.AddTransactionStore
import dev.rezyfr.trackerr.presentation.screens.create.transaction.store.AddTransactionStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class AddTransactionComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val action: (Action) -> Unit
) : ComponentContext by componentContext {

    private val addTransactionStore = AddTransactionStoreFactory(
        storeFactory
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AddTransactionStore.State> = addTransactionStore.stateFlow

    fun onEvent(event: AddTransactionStore.Intent) {
        addTransactionStore.accept(event)
    }

    fun onAction(action: Action) {
        action(action)
    }

    sealed class Action {
        object NavigateBack : Action()
        object Finish : Action()
    }
}