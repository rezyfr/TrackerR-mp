package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse

interface AuthService {
    suspend fun register(email: String, password: String, name: String) : NetworkResponse<BaseDto<String>>
    suspend fun login(email: String, password: String): NetworkResponse<BaseDto<String>>
    suspend fun checkToken() : NetworkResponse<BaseDto<Unit>>
}