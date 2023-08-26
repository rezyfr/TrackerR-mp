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
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 10000
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { _, response -> !response.status.isSuccess() }
            retryOnExceptionIf { _, cause -> cause is HttpRequestTimeoutException }
            delayMillis { 3000L } // retries in 3, 6, 9, etc. seconds
        }
        install(ContentNegotiation) {
            json(json)
        }
        install(Logging) {
            logger = Logger.DEFAULT
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