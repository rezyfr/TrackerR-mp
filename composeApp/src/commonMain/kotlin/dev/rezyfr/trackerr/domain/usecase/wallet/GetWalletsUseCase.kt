package dev.rezyfr.trackerr.domain.usecase.wallet

import dev.rezyfr.trackerr.data.mapper.WalletMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleFlowResult
import dev.rezyfr.trackerr.domain.model.WalletModel
import dev.rezyfr.trackerr.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow

class GetWalletsUseCase(
    private val repository: WalletRepository,
    private val mapper: WalletMapper
): UseCase<UiResult<List<WalletModel>>, Nothing> {
    override fun executeFlow(params: Nothing?): Flow<UiResult<List<WalletModel>>> {
        return handleFlowResult(
            execute = {
                repository.getWallets()
            },
            onSuccess = { list ->
                list.map { mapper.mapResponseToDomain(it) }
            }
        )
    }
}