package dev.rezyfr.trackerr.presentation.screens.register

import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.usecase.user.RegisterUseCase
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ScreenModel {

    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + ioDispatcher
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private var _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.update { _uiState.value.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { _uiState.value.copy(password = password) }
    }

    fun onNameChange(name: String) {
        _uiState.update { _uiState.value.copy(name = name) }
    }

    fun register() {
        viewModelScope.launch {
            _uiState.update { _uiState.value.copy(registerResult = UiResult.Loading) }
            val (_, email, password, name) = _uiState.value
            registerUseCase.execute(RegisterUseCase.Params(email, password, name)).handleResult(
                ifError = { ex ->
                    _uiState.update { _uiState.value.copy(registerResult = UiResult.Error(ex)) }
                },
                ifSuccess = { res ->
                    _uiState.update { _uiState.value.copy(registerResult = UiResult.Success(res)) }
                }
            )
        }
    }
}

data class RegisterUiState (
    val registerResult: UiResult<Unit> = UiResult.Uninitialized,
    val email: String = "",
    val password: String = "",
    val name: String = ""
)