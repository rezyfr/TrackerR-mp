package dev.rezyfr.trackerr.domain.usecase

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.repository.AuthRepository

class CheckTokenUseCase(
    private val authRepository: AuthRepository
): UseCase<UiResult<Boolean>, Unit> {
    override suspend fun execute(params: Unit): UiResult<Boolean> {
        return handleResult(
            execute = {
                authRepository.checkToken()
            },
            onSuccess = {
                it.status
            },
        )
    }
}