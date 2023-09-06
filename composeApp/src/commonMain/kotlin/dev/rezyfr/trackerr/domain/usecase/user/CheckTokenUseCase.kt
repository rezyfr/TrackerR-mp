package dev.rezyfr.trackerr.domain.usecase.user

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleFlowResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class CheckTokenUseCase(
    private val authRepository: AuthRepository
): UseCase<UiResult<Boolean>, Unit> {
    override fun executeFlow(params: Unit?): Flow<UiResult<Boolean>> {
        return handleFlowResult(
            execute = {
                authRepository.checkToken()
            },
            onSuccess = { true },
        )
    }
}