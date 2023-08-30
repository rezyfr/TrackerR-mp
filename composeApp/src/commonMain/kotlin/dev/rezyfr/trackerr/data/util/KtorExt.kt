package dev.rezyfr.trackerr.data.util

import dev.rezyfr.trackerr.data.dto.BaseDto
import dev.rezyfr.trackerr.data.dto.NetworkResponse
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

suspend inline fun <reified T> execute(
    block: () -> HttpResponse
): NetworkResponse<T> {
    val result = block()
    return if (result.status.isSuccess()) NetworkResponse.Success(result.body())
    else NetworkResponse.Failure(Exception((result.body() as BaseDto<Unit>).message))
}

inline fun <reified T> HttpRequestBuilder.setJsonBody(body: T) {
    contentType(ContentType.Application.Json)
    setBody(body)
}
