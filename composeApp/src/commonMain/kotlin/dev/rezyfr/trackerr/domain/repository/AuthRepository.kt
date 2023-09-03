package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse

interface AuthRepository {
    suspend fun register(email: String, password: String, name: String) : NetworkResponse<String>
    suspend fun login(email: String, password: String): NetworkResponse<String>

}