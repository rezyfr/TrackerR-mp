package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TokenResponse

interface AuthService {
    suspend fun register(email: String, password: String, name: String) : NetworkResponse<BaseDto<TokenResponse>>
    suspend fun login(email: String, password: String): NetworkResponse<BaseDto<TokenResponse>>
    suspend fun checkToken() : NetworkResponse<BaseDto<Unit>>
    suspend fun refreshToken() : NetworkResponse<BaseDto<TokenResponse>>
}