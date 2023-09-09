package dev.rezyfr.trackerr.domain.model

data class WalletModel(
    val id: Int,
    val name: String,
    val balance: Long,
    val icon: String
)
