package dev.rezyfr.trackerr.presentation.screens.create.category.store

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.IconType
import dev.rezyfr.trackerr.domain.usecase.category.CreateCategoryUseCase
import dev.rezyfr.trackerr.domain.usecase.icon.GetIconUseCase
import dev.rezyfr.trackerr.mainDispatcher
import dev.rezyfr.trackerr.presentation.theme.Violet100
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddCategoryExecutor :
    CoroutineExecutor<AddCategoryStore.Intent, Unit, AddCategoryStore.State, AddCategoryStore.Result, Nothing>(
        mainDispatcher
    ), KoinComponent {
    private val getIconUseCase by inject<GetIconUseCase>()
    private val createCategoryUseCase by inject<CreateCategoryUseCase>()

    init {
        getIcon()
    }

    private fun getIcon() {
        scope.launch {
            getIconUseCase.executeFlow(IconType.CATEGORY).collectLatest { result ->
                result.handleResult(
                    ifError = { ex ->
                        // Napier.e("Error getIconUseCase: ${ex.message}")
                    },
                    ifSuccess = { list ->
                        dispatch(AddCategoryStore.Result.GetIcon(list))
                    }
                )
            }
        }
    }

    private fun createCategory(state: AddCategoryStore.State) {
        Color.Black.toArgb()
        scope.launch {
            createCategoryUseCase.executeFlow(
                CreateCategoryUseCase.Params(
                    state.type,
                    state.name,
                    state.selectedColor.toArgb().toLong(),
                    state.iconList.find { it.id == state.selectedIcon!!.id }!!.id,
                )
            ).collectLatest {
                    it.handleResult(
                        ifError = {
                            // Napier.e("createAccount: ${it.message}")
                        },
                        ifSuccess = { category ->
                            dispatch(AddCategoryStore.Result.CreateCategory(UiResult.Success(category)))
                        }
                    )
                }
        }
    }

    override fun executeIntent(
        intent: AddCategoryStore.Intent,
        getState: () -> AddCategoryStore.State
    ) {
        when(intent) {
            is AddCategoryStore.Intent.CreateCategory -> createCategory(getState())
            is AddCategoryStore.Intent.OnColorChange -> dispatch(AddCategoryStore.Result.OnColorChange(intent.color))
            is AddCategoryStore.Intent.OnIconChange -> {
                getState().iconList.find { it.id == intent.icon }?.let {
                    dispatch(AddCategoryStore.Result.OnIconChange(it))
                }
            }
            is AddCategoryStore.Intent.OnNameChange -> dispatch(AddCategoryStore.Result.OnNameChange(intent.name))
            is AddCategoryStore.Intent.OnTypeChange -> dispatch(AddCategoryStore.Result.OnTypeChange(intent.type))
        }
    }
}