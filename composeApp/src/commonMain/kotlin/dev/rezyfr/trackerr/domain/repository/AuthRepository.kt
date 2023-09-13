package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.response.TokenResponse

interface AuthRepository {
    suspend fun register(email: String, password: String, name: String) : Result<TokenResponse>
    suspend fun login(email: String, password: String): Result<TokenResponse>
    fun saveAccessToken(token: String)
    fun saveRefreshToken(token: String)
    fun saveEmail(email: String)
    suspend fun checkToken() : Result<Unit>
}