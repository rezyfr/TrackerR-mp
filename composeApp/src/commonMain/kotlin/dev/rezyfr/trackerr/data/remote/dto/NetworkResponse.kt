package dev.rezyfr.trackerr.data.remote.dto

sealed class NetworkResponse<out T> {
    class Success<T>(val data: T) : NetworkResponse<T>()
    class Failure(val throwable: Throwable, val code: Int? = null) : NetworkResponse<Nothing>()
}

class TrException constructor(
    val code: Int = 0,
    override val message: String
) : Exception(message)

fun <T> NetworkResponse<BaseDto<T>>.handleResponse() : Result<T> {
    return when (this) {
        is NetworkResponse.Success -> Result.success(data.data!!)
        is NetworkResponse.Failure -> Result.failure(TrException(code ?: 0, throwable.message ?: ""))
    }
}