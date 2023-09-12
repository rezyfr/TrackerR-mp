package dev.rezyfr.trackerr.data.remote.dto.response


import dev.rezyfr.trackerr.domain.model.CategoryType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    @SerialName("amount")
    val amount: Double,
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("createdDate")
    val createdDate: String,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Int,
    @SerialName("wallet")
    val wallet: String,
    @SerialName("walletIcon")
    val walletIcon: String,
    @SerialName("category")
    val category: CategoryResponse
)