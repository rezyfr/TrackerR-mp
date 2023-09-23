package dev.rezyfr.trackerr.presentation.screens.reportwrap

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.presentation.screens.reportwrap.store.ReportWrapStore
import dev.rezyfr.trackerr.presentation.screens.reportwrap.store.ReportWrapStoreFactory
import kotlinx.coroutines.flow.StateFlow

class ReportWrapComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val action: (Action) -> Unit
) : ComponentContext by componentContext {
    private val reportWrapStore = ReportWrapStoreFactory(
        storeFactory
    ).create()
    val state: StateFlow<ReportWrapStore.State> = reportWrapStore.stateFlow
    fun onAction(action: Action) {
        action(action)
    }

    sealed class Action {
        object NavigateBack : Action()
    }
}