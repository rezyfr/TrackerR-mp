package dev.rezyfr.trackerr.data.util

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend inline fun <reified T> execute(
    block: () -> HttpResponse
): NetworkResponse<T> {
    val result = block()
    return try {
        if (result.status.isSuccess()) NetworkResponse.Success((result.body()) ?: throw Exception("Data is null"))
        else NetworkResponse.Failure(Exception((result.body() as BaseDto<Nothing>).message))
    } catch (e: Exception) {
        NetworkResponse.Failure(e)
    }
}

inline fun <reified T> HttpRequestBuilder.setJsonBody(body: T) {
    contentType(ContentType.Application.Json)
    setBody(body)
}

fun HttpRequestBuilder.setNoAuthHeader() {
    header("No-Authentication", true)
}
