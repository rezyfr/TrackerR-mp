package dev.rezyfr.trackerr.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
