package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse

interface AuthRepository {
    suspend fun register(email: String, password: String, name: String) : NetworkResponse<BaseDto<String>>
    suspend fun login(email: String, password: String): NetworkResponse<BaseDto<String>>
    fun saveToken(token: String)
    suspend fun checkToken() : NetworkResponse<BaseDto<Unit>>
}