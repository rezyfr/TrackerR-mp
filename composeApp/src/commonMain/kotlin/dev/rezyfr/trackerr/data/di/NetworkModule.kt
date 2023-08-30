package dev.rezyfr.trackerr.data.di

import TrackerR_Multiplatform.composeApp.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module



fun getNetworkModule(enableNetworkLogs: Boolean) = module {
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