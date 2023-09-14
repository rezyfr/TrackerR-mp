package dev.rezyfr.trackerr.presentation.screens.create.category.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory

class AddCategoryStoreFactory(
    private val storeFactory: StoreFactory
) {
    fun create() : AddCategoryStore = object : AddCategoryStore,
        Store<AddCategoryStore.Intent, AddCategoryStore.State, Nothing> by storeFactory.create(
            name = AddCategoryStore::class.simpleName,
            initialState = AddCategoryStore.State(),
            executorFactory = ::AddCategoryExecutor,
            reducer = AddCategoryReducer()
        ) {}
}