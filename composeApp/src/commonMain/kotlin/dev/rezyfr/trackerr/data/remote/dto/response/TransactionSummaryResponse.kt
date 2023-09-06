package dev.rezyfr.trackerr.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionSummaryResponse(
    @SerialName("totalExpense")
    val totalExpense: Long,
    @SerialName("totalIncome")
    val totalIncome: Long
)