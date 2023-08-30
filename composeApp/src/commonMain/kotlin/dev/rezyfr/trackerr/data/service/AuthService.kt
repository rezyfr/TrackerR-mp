package dev.rezyfr.trackerr.data.service

import dev.rezyfr.trackerr.data.dto.NetworkResponse

interface AuthService {
    suspend fun register(email: String, password: String, name: String) : NetworkResponse<String>
    suspend fun login(email: String, password: String): NetworkResponse<String>
}