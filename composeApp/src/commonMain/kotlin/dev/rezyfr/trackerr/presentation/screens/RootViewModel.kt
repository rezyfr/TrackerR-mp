package dev.rezyfr.trackerr.presentation.screens

import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.usecase.category.SyncCategoryUseCase
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RootViewModel(
    private val syncCategoryUseCase: SyncCategoryUseCase
) : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + ioDispatcher
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: MutableStateFlow<String?> = _errorMessage

    init {
        syncCategory()
    }

    private fun syncCategory() {
        viewModelScope.launch {
            syncCategoryUseCase.execute(null).handleResult(
                ifError = { ex ->
                    _errorMessage.value = ex.message
                },
                ifSuccess = { result ->
                    // Do nothing
                }
            )
        }
    }
}