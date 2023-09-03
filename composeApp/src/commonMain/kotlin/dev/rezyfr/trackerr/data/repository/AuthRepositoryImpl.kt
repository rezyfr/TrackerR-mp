package dev.rezyfr.trackerr.data.repository


import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.handleResponse
import dev.rezyfr.trackerr.data.remote.service.AuthService
import dev.rezyfr.trackerr.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun register(email: String, password: String, name: String): NetworkResponse<String> {
        return handleResponse { authService.register(email, password, name) }
    }

    override suspend fun login(email: String, password: String): NetworkResponse<String> {
        return handleResponse { authService.login(email, password) }
    }
}