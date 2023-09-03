package dev.rezyfr.trackerr.data.remote.dto

import kotlinx.serialization.SerialName

sealed class NetworkResponse<out T> {
    class Success<T>(val data: T) : NetworkResponse<T>()
    class Failure(val throwable: Throwable) : NetworkResponse<Nothing>()
}
