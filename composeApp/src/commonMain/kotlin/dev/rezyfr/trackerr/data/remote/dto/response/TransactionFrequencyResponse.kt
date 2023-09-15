package dev.rezyfr.trackerr.data.remote.dto.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionFrequencyResponse(
    @SerialName("expenseAmount")
    val expenseAmount: Long,
    @SerialName("incomeAmount")
    val incomeAmount: Long,
    @SerialName("index")
    val index: Int
)