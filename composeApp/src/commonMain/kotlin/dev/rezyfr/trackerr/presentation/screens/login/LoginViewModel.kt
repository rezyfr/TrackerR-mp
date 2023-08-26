package dev.rezyfr.trackerr.presentation.screens.login

import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.usecase.LoginUseCase
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel (
    private val loginUseCase: LoginUseCase
) : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + ioDispatcher
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private var _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.update { _uiState.value.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { _uiState.value.copy(password = password) }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.execute(LoginUseCase.Params(email, password)).handleResult(
                ifError = { ex ->
                    _uiState.update { _uiState.value.copy(loginResult = UiResult.Error(ex)) }
                },
                ifSuccess = { res ->
                    _uiState.update { _uiState.value.copy(loginResult = UiResult.Success(res)) }
                }
            )
        }
    }
}

data class LoginUiState (
    val loginResult: UiResult<String> = UiResult.Uninitialized,
    val email: String = "",
    val password: String = ""
)