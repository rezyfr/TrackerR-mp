package dev.rezyfr.trackerr.screens.auth

import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AuthViewModel (
//    private val userRepository: UserRepository,
//    private val loginUseCase: LoginUseCase
) : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + ioDispatcher
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private var _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
    val uiState: StateFlow<LoginUiState> = _uiState

    init {
        viewModelScope.launch {
//            userRepository.isLoggedIn.collectLatest {
//                if (it) _uiState.value = LoginUiState.Success
//            }
        }
    }

    fun storeUserData(gsa: Unit) {
//        viewModelScope.launch {
//            loginUseCase(gsa).collectLatest {
//                if (it.isSuccess) {
//                    _uiState.value = LoginUiState.Success
//                } else {
//                    _uiState.value = LoginUiState.Error(it.exceptionOrNull()!!)
//                }
//            }
//        }
    }
}

sealed interface LoginUiState {
    data object Loading : LoginUiState
    data class Error(val throwable: Throwable) : LoginUiState
    data object Success : LoginUiState
}
