package dev.rezyfr.trackerr.presentation.screens.main.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.presentation.screens.main.home.store.HomeStore
import dev.rezyfr.trackerr.presentation.screens.main.home.store.HomeStoreFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

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