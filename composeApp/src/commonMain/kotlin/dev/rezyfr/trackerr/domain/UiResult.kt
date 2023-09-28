package dev.rezyfr.trackerr.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class UiResult<out T> {
    data class Success<T>(val data: T) : UiResult<T>()
    object SuccessEmpty : UiResult<Nothing>()
    data class Error(val exception: Exception) : UiResult<Nothing>()
    data object Loading : UiResult<Nothing>()
    data object Uninitialized : UiResult<Nothing>()

    fun isSuccess() = this is Success
    fun asSuccess(): T? = (this as? Success<T>)?.data
    fun isLoading() = this is Loading
}

suspend fun <R, T> handleResult(
    execute: suspend () -> Result<T>,
    onSuccess: (T) -> R,
): UiResult<R> {
    execute().onSuccess {
        return UiResult.Success(onSuccess(it))
    }.onFailure {
        return UiResult.Error(Exception(it.message))
    }
    return UiResult.Error(Exception("Something went wrong"))
}

fun <R, T> handleFlowResult(
    execute: suspend () -> Result<T>,
    onSuccess: suspend (T) -> R
) : Flow<UiResult<R>> = flow {
    emit(UiResult.Loading)
    execute().onSuccess {
        if (it is Collection<*> && it.isEmpty()) {
            emit(UiResult.SuccessEmpty)
        } else {
            emit(UiResult.Success(onSuccess(it)))
        }
    }.onFailure {
        emit(UiResult.Error(Exception(it.message)))
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