package dev.rezyfr.trackerr.presentation.screens.create.transaction

import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

class AddTransactionViewModel : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + ioDispatcher
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private val _state = MutableStateFlow(AddTransactionState())
    val state: StateFlow<AddTransactionState> = _state

    fun onChangeType(type: String) {
        _state.value = _state.value.copy(type = type)
    }
}

data class AddTransactionState(
    val type: String = "Expense",
)