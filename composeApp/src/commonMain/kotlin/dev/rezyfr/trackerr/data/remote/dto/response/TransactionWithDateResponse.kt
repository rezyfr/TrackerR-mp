package dev.rezyfr.trackerr.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class TransactionWithDateResponse(
    val date: String,
    val transactions: List<TransactionResponse>
)
