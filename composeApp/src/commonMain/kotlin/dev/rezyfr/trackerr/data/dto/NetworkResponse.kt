package dev.rezyfr.trackerr.data.dto

sealed class NetworkResponse<out T> {
    class Success<T>(val data: T) : NetworkResponse<T>()
    class Failure(val throwable: Throwable) : NetworkResponse<Nothing>()
}

inline fun <reified T> handleResponse(
    block: () -> NetworkResponse<T>
): NetworkResponse<T> {
    return when (val result = block()) {
        is NetworkResponse.Success -> NetworkResponse.Success(result.data)
        is NetworkResponse.Failure -> NetworkResponse.Failure(result.throwable)
    }
}