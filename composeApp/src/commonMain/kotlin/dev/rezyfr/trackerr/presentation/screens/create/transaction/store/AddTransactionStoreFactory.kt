package dev.rezyfr.trackerr.presentation.screens.create.transaction.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import org.koin.core.component.KoinComponent

class AddTransactionStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {
    fun create(): AddTransactionStore = object : AddTransactionStore,
        Store<AddTransactionStore.Intent, AddTransactionStore.State, Nothing> by storeFactory.create(
            name = AddTransactionStore::class.simpleName,
            initialState = AddTransactionStore.State(),
            executorFactory = ::AddTransactionExecutor,
            reducer = AddTransactionReducer()
        ) {
    }
}