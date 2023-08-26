package dev.rezyfr.trackerr.domain

import dev.rezyfr.trackerr.data.dto.NetworkResponse

sealed class UiResult<out T> {
    data class Success<T>(val data: T) : UiResult<T>()
    data class Error(val exception: Exception) : UiResult<Nothing>()
    data object Loading : UiResult<Nothing>()
    data object Uninitialized : UiResult<Nothing>()
}

inline fun <reified T> handleResult(
    block: () -> NetworkResponse<T>
): UiResult<T> {
    return when (val result = block()) {
        is NetworkResponse.Success -> UiResult.Success(result.data)
        is NetworkResponse.Failure -> UiResult.Error(Exception(result.throwable.message))
    }
}

inline fun <reified T> UiResult<T>.handleResult(
    ifError: (Exception) -> Unit,
    ifSuccess: (T) -> Unit
) {
    when (this) {
        is UiResult.Success -> ifSuccess(data)
        is UiResult.Error -> ifError(exception)
        else -> Unit
    }
}