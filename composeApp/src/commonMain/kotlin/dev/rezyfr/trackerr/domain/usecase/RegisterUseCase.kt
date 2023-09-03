package dev.rezyfr.trackerr.domain.usecase

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) : UseCase<UiResult<String>, RegisterUseCase.Params> {

    override suspend fun execute(params: Params): UiResult<String> {
        return handleResult { authRepository.register(params.email, params.password, params.name) }
    }

    data class Params(
        val email: String,
        val password: String,
        val name: String
    )
}