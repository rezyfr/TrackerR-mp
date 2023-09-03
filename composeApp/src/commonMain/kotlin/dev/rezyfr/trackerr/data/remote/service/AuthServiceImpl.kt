package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.request.LoginRequest
import dev.rezyfr.trackerr.data.remote.dto.request.RegisterRequest
import dev.rezyfr.trackerr.data.util.execute
import dev.rezyfr.trackerr.data.util.setJsonBody
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class AuthServiceImpl(
    private val httpClient: HttpClient,
    baseUrl: String
) : AuthService {

    private val register = "$baseUrl/register"
    private val login = "$baseUrl/user/login"

    override suspend fun register(email: String, password: String, name: String): NetworkResponse<String> {
        return execute {
            httpClient.post {
                url(register)
                setJsonBody(
                    RegisterRequest(
                        email = email,
                        password = password,
                        name = name
                    )
                )
            }
        }
    }

    override suspend fun login(email: String, password: String): NetworkResponse<String> {
        return execute {
            httpClient.post {
                url(login)
                setJsonBody(
                    LoginRequest(
                        email = email,
                        password = password,
                    )
                )
            }.body()
        }
    }
}