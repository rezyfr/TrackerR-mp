package dev.rezyfr.trackerr.domain.usecase

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) : UseCase<UiResult<Unit>, RegisterUseCase.Params> {

    override suspend fun execute(params: Params): UiResult<Unit> {
        return handleResult(execute = {
            authRepository.register(
                params.email,
                params.password,
                params.name
            )
        }, onSuccess = {
            authRepository.saveToken(it.data.orEmpty())
        })
    }

    data class Params(
        val email: String,
        val password: String,
        val name: String
    )
}