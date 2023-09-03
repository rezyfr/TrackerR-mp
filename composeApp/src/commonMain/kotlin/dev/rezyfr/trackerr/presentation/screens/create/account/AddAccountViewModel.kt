package dev.rezyfr.trackerr.presentation.screens.create.account

import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.AccountModel
import dev.rezyfr.trackerr.domain.model.IconModel
import dev.rezyfr.trackerr.domain.model.IconType
import dev.rezyfr.trackerr.domain.usecase.GetIconUseCase
import dev.rezyfr.trackerr.ioDispatcher
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddAccountViewModel(
    private val getIconUseCase: GetIconUseCase
) : ScreenModel {

    private val job = SupervisorJob()
    private val coroutineContextX: CoroutineContext = job + ioDispatcher
    private val viewModelScope = CoroutineScope(coroutineContextX)

    private var _uiState = MutableStateFlow(AddAccountState())
    val uiState: StateFlow<AddAccountState> = _uiState

    init {
        getIconUseCase()
    }

    private fun getIconUseCase() {
        viewModelScope.launch {
            _uiState.update { it.copy(result = UiResult.Loading) }
            getIconUseCase.executeFlow(IconType.WALLET).collectLatest { result ->
                result.handleResult(
                    ifError = { ex ->
                        Napier.e("Error getIconUseCase: ${ex.message}")
                    },
                    ifSuccess = {  list ->
                        _uiState.update { it.copy(iconList = list) }
                    }
                )
            }
        }
    }

    fun onChangeName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onChangeBalance(balance: TextFieldValue) {
        _uiState.update { it.copy(balance = balance) }
    }

    fun onSelectIcon(iconId: Int) {
        _uiState.update { it.copy(icon = iconId) }
    }
}

data class AddAccountState(
    val name: String = "",
    val balance: TextFieldValue = TextFieldValue("0"),
    val icon: Int = -1,
    val result: UiResult<AccountModel> = UiResult.Uninitialized,
    val iconList: List<IconModel> = listOf()
)