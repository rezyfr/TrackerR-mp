package dev.rezyfr.trackerr.domain.usecase.wallet

import dev.rezyfr.trackerr.data.remote.dto.request.CreateWalletRequest
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleFlowResult
import dev.rezyfr.trackerr.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow

class CreateWalletUseCase(
    private val repository: WalletRepository
) : UseCase<UiResult<Unit>, CreateWalletUseCase.Params> {

    override fun executeFlow(params: Params?): Flow<UiResult<Unit>> {
        return handleFlowResult(
            execute = {
                if (params == null) throw Exception("Params cannot be null")
                repository.createWallet(
                    CreateWalletRequest(
                        params.name,
                        params.balance,
                        iconId = params.icon
                    )
                )
            },
            onSuccess = {
                // Nothing
            }
        )
    }

    data class Params(
        val name: String,
        val balance: Int,
        val icon: Int
    )
}