package dev.rezyfr.trackerr.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String,
    val email: String
)
