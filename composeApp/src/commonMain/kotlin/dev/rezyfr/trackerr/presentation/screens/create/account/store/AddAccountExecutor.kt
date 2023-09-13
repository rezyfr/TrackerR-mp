package dev.rezyfr.trackerr.presentation.screens.create.account.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.IconType
import dev.rezyfr.trackerr.domain.usecase.icon.GetIconUseCase
import dev.rezyfr.trackerr.domain.usecase.wallet.CreateWalletUseCase
import dev.rezyfr.trackerr.mainDispatcher
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddAccountExecutor :
    CoroutineExecutor<AddAccountStore.Intent, Unit, AddAccountStore.State, AddAccountStore.Result, Nothing>(
        mainDispatcher
    ), KoinComponent {
    private val getIconUseCase by inject<GetIconUseCase>()
    private val createWalletUseCase by inject<CreateWalletUseCase>()

    init {
        getIcon()
    }

    private fun getIcon() {
        scope.launch {
            getIconUseCase.executeFlow(IconType.WALLET).collectLatest { result ->
                result.handleResult(
                    ifError = { ex ->
                        Napier.e("Error getIconUseCase: ${ex.message}")
                    },
                    ifSuccess = { list ->
                        dispatch(AddAccountStore.Result.GetIcon(list))
                    }
                )
            }
        }
    }

    private fun createAccount(state: AddAccountStore.State) {
        scope.launch {
            createWalletUseCase.executeFlow(
                CreateWalletUseCase.Params(
                    state.name,
                    state.balance.text.toInt(),
                    icon = state.iconList.find { it.id == state.icon }!!.id
                )
            )
                .collectLatest {
                    it.handleResult(
                        ifError = {
                            Napier.e("createAccount: ${it.message}")
                        },
                        ifSuccess = { wallet ->
                            dispatch(AddAccountStore.Result.CreateWallet(UiResult.Success(wallet)))
                        }
                    )
                }
        }
    }

    override fun executeIntent(
        intent: AddAccountStore.Intent,
        getState: () -> AddAccountStore.State
    ) {
        when(intent) {
            is AddAccountStore.Intent.CreateWallet -> createAccount(getState())
            is AddAccountStore.Intent.OnBalanceChange -> {
                dispatch(AddAccountStore.Result.OnBalanceChange(intent.balance))
            }
            is AddAccountStore.Intent.OnIconChange -> {
                dispatch(AddAccountStore.Result.OnIconChange(intent.icon))
            }
            is AddAccountStore.Intent.OnNameChange -> {
                dispatch(AddAccountStore.Result.OnNameChange(intent.name))
            }
        }
    }
}