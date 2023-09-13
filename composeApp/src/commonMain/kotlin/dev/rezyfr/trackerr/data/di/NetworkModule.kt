package dev.rezyfr.trackerr.data.di

import TrackerR_Multiplatform.composeApp.BuildConfig
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.request.RefreshTokenRequest
import dev.rezyfr.trackerr.data.remote.dto.response.TokenResponse
import dev.rezyfr.trackerr.data.remote.service.AuthService
import dev.rezyfr.trackerr.data.util.SettingsConstant
import dev.rezyfr.trackerr.data.util.setJsonBody
import dev.rezyfr.trackerr.utils.createSettings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun getNetworkModule(enableNetworkLogs: Boolean) = module {
    factory<Settings> { createSettings() }
    single(named("baseUrl")) { BuildConfig.BASE_URL }
    single { createHttpClient(get(), get(named("baseUrl")), get(), enableNetworkLogs) }
    single { createJson() }
}

fun createHttpClient(json: Json, url: String, settings: Settings, enableNetworkLogs: Boolean) =
    HttpClient {
        defaultRequest {
            url(url)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryOnExceptionIf { _, cause -> cause is HttpRequestTimeoutException }
            delayMillis { 3000L }
        }
        install(ContentNegotiation) {
            json(json)
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = if (enableNetworkLogs) LogLevel.ALL else LogLevel.NONE
        }
        install(Auth) {
            bearer {
                refreshTokens {
                    client.post {
                        markAsRefreshTokenRequest()
                        url("v1/user/refresh-token")
                        setJsonBody(RefreshTokenRequest(settings[SettingsConstant.KEY_REFRESH_TOKEN, ""], settings[SettingsConstant.KEY_EMAIL, ""]))
                    }.body<BaseDto<TokenResponse>>().let { data ->
                        if(data.data?.accessToken != null && data.data.refreshToken != null) {
                            BearerTokens(
                                accessToken = data.data.accessToken,
                                refreshToken = data.data.refreshToken
                            )
                        }
                        else {
                            null
                        }
                    }
                }
            }
        }
    }

fun createJson() = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    prettyPrint = true
    coerceInputValues = true
}