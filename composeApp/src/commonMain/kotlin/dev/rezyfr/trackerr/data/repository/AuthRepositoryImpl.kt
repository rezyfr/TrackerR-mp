package dev.rezyfr.trackerr.data.repository


import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.handleResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TokenResponse
import dev.rezyfr.trackerr.data.remote.service.AuthService
import dev.rezyfr.trackerr.data.util.SettingsConstant
import dev.rezyfr.trackerr.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val settings: Settings
) : AuthRepository {

    override suspend fun register(email: String, password: String, name: String): Result<TokenResponse> {
        return authService.register(email, password, name).handleResponse()
    }

    override suspend fun login(email: String, password: String): Result<TokenResponse> {
        return authService.login(email, password).handleResponse()
    }

    override fun saveAccessToken(token: String) {
        settings.putString(SettingsConstant.KEY_ACCESS_TOKEN, token)
    }

    override fun saveRefreshToken(token: String) {
        settings.putString(SettingsConstant.KEY_REFRESH_TOKEN, token)
    }

    override fun saveEmail(email: String) {
        settings.putString(SettingsConstant.KEY_EMAIL, email)
    }

    override suspend fun checkToken(): Result<Unit> {
        return authService.checkToken().handleResponse()
    }
}