package dev.rezyfr.trackerr.data.util

import dev.rezyfr.trackerr.data.dto.NetworkResponse
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend inline fun <reified T> execute(
    block: () -> HttpResponse
): NetworkResponse<T> {
    return try {
        val apiCallResponse = block()
        NetworkResponse.Success(apiCallResponse.body())
    } catch (e: RedirectResponseException) {
        // 3xx - responses
        NetworkResponse.Failure(e)
    } catch (e: ClientRequestException) {
        // 4xx - responses
        NetworkResponse.Failure(e)
    } catch (e: NoTransformationFoundException) {
        NetworkResponse.Failure(e)
    } catch (e: ServerResponseException) {
        // 5xx - responses
        NetworkResponse.Failure(e)
    } catch (e: Exception) {
        NetworkResponse.Failure(e)
    }
}

inline fun <reified T> HttpRequestBuilder.setJsonBody(body: T) {
    contentType(ContentType.Application.Json)
    setBody(body)
}
