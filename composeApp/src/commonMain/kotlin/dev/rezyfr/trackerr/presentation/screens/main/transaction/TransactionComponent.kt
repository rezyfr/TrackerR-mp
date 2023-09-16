package dev.rezyfr.trackerr.presentation.screens.main.transaction

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.presentation.screens.main.transaction.store.TransactionStore
import dev.rezyfr.trackerr.presentation.screens.main.transaction.store.TransactionStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class TransactionComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : ComponentContext by componentContext {

    private val transactionStore = TransactionStoreFactory(
        storeFactory = storeFactory
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<TransactionStore.State> = transactionStore.stateFlow

    fun onEvent(event: TransactionStore.Intent) {
        transactionStore.accept(event)
    }
}