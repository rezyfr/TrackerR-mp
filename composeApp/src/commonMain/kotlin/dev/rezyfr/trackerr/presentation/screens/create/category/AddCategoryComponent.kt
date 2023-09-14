package dev.rezyfr.trackerr.presentation.screens.create.category

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.presentation.screens.create.category.store.AddCategoryStore
import dev.rezyfr.trackerr.presentation.screens.create.category.store.AddCategoryStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class AddCategoryComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val action: (Action) -> Unit
) : ComponentContext by componentContext {

    private val addCategoryStore = AddCategoryStoreFactory(
        storeFactory
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AddCategoryStore.State> = addCategoryStore.stateFlow

    fun onEvent(event: AddCategoryStore.Intent) {
        addCategoryStore.accept(event)
    }

    fun onAction(action: Action) {
        action(action)
    }

    sealed class Action {
        object NavigateBack : Action()
    }
}