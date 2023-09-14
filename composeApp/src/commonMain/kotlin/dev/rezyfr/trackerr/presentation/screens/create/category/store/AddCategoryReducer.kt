package dev.rezyfr.trackerr.presentation.screens.create.category.store

import com.arkivanov.mvikotlin.core.store.Reducer

class AddCategoryReducer : Reducer<AddCategoryStore.State, AddCategoryStore.Result> {
    override fun AddCategoryStore.State.reduce(msg: AddCategoryStore.Result): AddCategoryStore.State {
        return when (msg) {
            is AddCategoryStore.Result.CreateCategory -> copy(result = msg.result)
            is AddCategoryStore.Result.GetIcon -> copy(iconList = msg.result)
            is AddCategoryStore.Result.OnColorChange -> copy(selectedColor = msg.color)
            is AddCategoryStore.Result.OnIconChange -> copy(selectedIcon = msg.icon)
            is AddCategoryStore.Result.OnNameChange -> copy(name = msg.name)
            is AddCategoryStore.Result.OnTypeChange -> copy(type = msg.type)
        }
    }
}