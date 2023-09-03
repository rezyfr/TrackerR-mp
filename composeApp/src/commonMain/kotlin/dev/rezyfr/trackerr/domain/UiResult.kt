package dev.rezyfr.trackerr.domain

import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

inline fun <reified T, R> handleResult(
    block: () -> NetworkResponse<T>,
    map: (T) -> R
): UiResult<R> {
    return when (val result = block()) {
        is NetworkResponse.Success -> UiResult.Success(result.data.let(map))
        is NetworkResponse.Failure -> UiResult.Error(Exception(result.throwable.message))
    }
}

fun <R, T> handleFlowResult(
    execute: suspend () -> NetworkResponse<T>,
    onSuccess: (T) -> R
) : Flow<UiResult<R>> = flow {
    emit(UiResult.Loading)
    when (val result = execute()) {
        is NetworkResponse.Success -> emit(UiResult.Success(onSuccess(result.data)))
        is NetworkResponse.Failure -> emit(UiResult.Error(Exception(result.throwable.message)))
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