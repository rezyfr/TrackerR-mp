package dev.rezyfr.trackerr.domain.repository

interface AuthRepository {
    suspend fun register(email: String, password: String, name: String) : Result<String>
    suspend fun login(email: String, password: String): Result<String>
    fun saveToken(token: String)
    suspend fun checkToken() : Result<Unit>
}