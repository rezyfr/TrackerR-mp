package dev.rezyfr.trackerr.domain.usecase.wallet

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.repository.WalletRepository

class GetWalletBalanceUseCase(
    private val walletRepository: WalletRepository
) : UseCase<UiResult<Long>, Nothing?> {
    override suspend fun execute(params: Nothing?): UiResult<Long> {
        return handleResult(
            execute = {
                walletRepository.getWalletBalance()
            },
            onSuccess = {
                it
            }
        )
    }
}