package dev.rezyfr.trackerr.data.remote.dto.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateTransactionRequest(
    @SerialName("amount")
    val amount: Double,
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("createdDate")
    val createdDate: String,
    @SerialName("description")
    val description: String,
    @SerialName("walletId")
    val walletId: Int
)