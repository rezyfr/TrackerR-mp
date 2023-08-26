package dev.rezyfr.trackerr.domain.usecase

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) : UseCase<UiResult<String>, LoginUseCase.Params> {

    override suspend fun execute(params: Params): UiResult<String> {
        return handleResult { authRepository.login(params.email, params.password) }
    }

    data class Params(
        val email: String,
        val password: String
    )
}