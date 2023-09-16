package dev.rezyfr.trackerr.presentation.screens.main.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.presentation.screens.main.home.store.HomeStore
import dev.rezyfr.trackerr.presentation.screens.main.home.store.HomeStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class HomeComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
) : ComponentContext by componentContext {

    private val homeStore = HomeStoreFactory(
        storeFactory = storeFactory
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<HomeStore.State> = homeStore.stateFlow

    fun onEvent(event: HomeStore.Intent) {
        homeStore.accept(event)
    }
}