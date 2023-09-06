package dev.rezyfr.trackerr.presentation.screens.create.account

import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.IconModel
import dev.rezyfr.trackerr.domain.model.IconType
import dev.rezyfr.trackerr.domain.usecase.wallet.CreateWalletUseCase
import dev.rezyfr.trackerr.domain.usecase.icon.GetIconUseCase
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
    private val getIconUseCase: GetIconUseCase,
    private val createWalletUseCase: CreateWalletUseCase
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
                    ifSuccess = { list ->
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

    fun onContinue() {
        viewModelScope.launch {
            val state = uiState.value
            createWalletUseCase.executeFlow(
                CreateWalletUseCase.Params(
                    name = state.name,
                    balance = state.balance.text.toInt(),
                    icon = state.iconList.find { it.id == state.icon }!!.id
                )
            ).collectLatest {
                it.handleResult(
                    ifError = { ex ->
                        _uiState.update { it.copy(result = UiResult.Error(ex)) }
                    },
                    ifSuccess = {
                        _uiState.update { it.copy(result = UiResult.Success(Unit)) }
                    }
                )
            }
        }
    }
}

data class AddAccountState(
    val name: String = "",
    val balance: TextFieldValue = TextFieldValue("0"),
    val icon: Int = -1,
    val result: UiResult<Unit> = UiResult.Uninitialized,
    val iconList: List<IconModel> = listOf()
)