package dev.rezyfr.trackerr.data.remote.dto.request

import kotlinx.serialization.Serializable


@Serializable
data class CreateWalletRequest(
    val name: String,
    val balance: Int,
    val color: Int = 0,
    val iconId: Int
)
