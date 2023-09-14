package dev.rezyfr.trackerr.presentation.screens.create.category.store

import androidx.compose.ui.graphics.Color
import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.IconModel
import dev.rezyfr.trackerr.presentation.component.ui.BottomSheet
import dev.rezyfr.trackerr.presentation.theme.IconColors

interface AddCategoryStore : Store<AddCategoryStore.Intent, AddCategoryStore.State, Nothing>{
    sealed class Intent {
        data class OnNameChange(val name: String) : Intent()
        data class OnIconChange(val icon: Int) : Intent()
        data class OnColorChange(val color: Color) : Intent()
        data class OnTypeChange(val type: CategoryType) : Intent()
        object CreateCategory : Intent()
    }

    sealed class Result {
        data class OnNameChange(val name: String) : Result()
        data class OnIconChange(val icon: IconModel) : Result()
        data class OnColorChange(val color: Color) : Result()
        data class OnTypeChange(val type: CategoryType) : Result()
        data class CreateCategory(val result: UiResult<Unit>) : Result()
        data class GetIcon(val result: List<IconModel>) : Result()
    }

    data class State(
        val name: String = "",
        val result: UiResult<Unit> = UiResult.Uninitialized,
        val type: CategoryType = CategoryType.EXPENSE,
        val iconList: List<IconModel> = listOf(),
        val iconSheet: BottomSheet = BottomSheet(),
        val selectedIcon: IconModel? = null,
        val colorList: List<Color> = IconColors,
        val selectedColor : Color = colorList.first()
    )
}