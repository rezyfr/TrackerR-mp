package dev.rezyfr.trackerr.data.di

import TrackerR_Multiplatform.composeApp.BuildConfig
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.rezyfr.trackerr.data.util.SettingsConstant
import dev.rezyfr.trackerr.presentation.screens.create.CreateScreens.AddAccount.header
import dev.rezyfr.trackerr.utils.createSettings
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.http.*
import io.ktor.http.auth.AuthScheme
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.koin.core.qualifier.named
import org.koin.dsl.module



fun getNetworkModule(enableNetworkLogs: Boolean) = module {
    factory<Settings> { createSettings() }
    single(named("baseUrl")) { BuildConfig.BASE_URL }
    single { createHttpClient(get(), enableNetworkLogs, get()) }
    single { createJson() }
}

fun createHttpClient(json: Json, enableNetworkLogs: Boolean, settings: Settings) =
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
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        settings[SettingsConstant.KEY_TOKEN, ""],
                        ""
                    )
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