package dev.rezyfr.trackerr.presentation.screens.create.account.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory

class AddAccountStoreFactory(
    private val storeFactory: StoreFactory
) {
    fun create() : AddAccountStore = object : AddAccountStore,
        Store<AddAccountStore.Intent, AddAccountStore.State, Nothing> by storeFactory.create(
            name = AddAccountStore::class.simpleName,
            initialState = AddAccountStore.State(),
            executorFactory = ::AddAccountExecutor,
            reducer = AddAccountReducer()
        ) {}
}