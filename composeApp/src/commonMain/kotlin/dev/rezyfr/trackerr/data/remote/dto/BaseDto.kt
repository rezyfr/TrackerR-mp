package dev.rezyfr.trackerr.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseDto<T>(
    @SerialName("success")
    val isSuccess: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: T? = null
)