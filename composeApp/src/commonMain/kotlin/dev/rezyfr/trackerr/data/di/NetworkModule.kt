package dev.rezyfr.trackerr.data.di

import TrackerR_Multiplatform.composeApp.BuildConfig
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.rezyfr.trackerr.data.util.SettingsConstant
import dev.rezyfr.trackerr.utils.createSettings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun getNetworkModule(enableNetworkLogs: Boolean) = module {
    factory<Settings> { createSettings() }
    single(named("baseUrl")) { BuildConfig.BASE_URL }
    single { createHttpClient(get(), enableNetworkLogs) }
    single { createJson() }
}

fun createHttpClient(json: Json, enableNetworkLogs: Boolean) =
    HttpClient {
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
    }

fun createJson() = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    prettyPrint = true
    coerceInputValues = true
}