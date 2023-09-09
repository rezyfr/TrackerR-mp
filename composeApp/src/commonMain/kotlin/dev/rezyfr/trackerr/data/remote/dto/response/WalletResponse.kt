package dev.rezyfr.trackerr.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WalletResponse(
    val id: Int,
    val name: String,
    val balance: Long,
    val color: Int,
    val icon: String,
)
