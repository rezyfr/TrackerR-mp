package dev.rezyfr.trackerr.data.remote.service

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.request.LoginRequest
import dev.rezyfr.trackerr.data.remote.dto.request.RegisterRequest
import dev.rezyfr.trackerr.data.util.SettingsConstant
import dev.rezyfr.trackerr.data.util.execute
import dev.rezyfr.trackerr.data.util.setAuthHeader
import dev.rezyfr.trackerr.data.util.setJsonBody
import dev.rezyfr.trackerr.data.util.setNoAuthHeader
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.isSuccess

class AuthServiceImpl(
    private val httpClient: HttpClient,
    private val settings: Settings,
    baseUrl: String
) : AuthService {

    private val register = "$baseUrl/user/register"
    private val login = "$baseUrl/user/login"
    private val checkToken = "$baseUrl/user/check-token"

    override suspend fun register(
        email: String,
        password: String,
        name: String
    ): NetworkResponse<BaseDto<String>> {
        return execute {
            httpClient.post {
                url(register)
                setNoAuthHeader()
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

    override suspend fun login(email: String, password: String): NetworkResponse<BaseDto<String>> {
        return execute {
            httpClient.post {
                url(login)
                setNoAuthHeader()
                setJsonBody(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )
            }.body()
        }
    }

    override suspend fun checkToken(): NetworkResponse<BaseDto<Unit>> {
        return execute {
            httpClient.get {
                setAuthHeader(settings[SettingsConstant.KEY_TOKEN, ""])
                url(checkToken)
            }.body()
        }
    }
}