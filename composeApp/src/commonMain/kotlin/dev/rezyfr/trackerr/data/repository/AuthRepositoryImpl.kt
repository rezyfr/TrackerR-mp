package dev.rezyfr.trackerr.data.repository


import com.russhwolf.settings.Settings
import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.handleResponse
import dev.rezyfr.trackerr.data.remote.service.AuthService
import dev.rezyfr.trackerr.data.util.SettingsConstant
import dev.rezyfr.trackerr.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val settings: Settings
) : AuthRepository {

    override suspend fun register(email: String, password: String, name: String): Result<String> {
        return authService.register(email, password, name).handleResponse()
    }

    override suspend fun login(email: String, password: String): Result<String> {
        return authService.login(email, password).handleResponse()
    }

    override fun saveToken(token: String) {
        settings.putString(SettingsConstant.KEY_TOKEN, token)
    }

    override suspend fun checkToken(): Result<Unit> {
        return authService.checkToken().handleResponse()
    }
}