package dev.rezyfr.trackerr.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionReportResponse(
    @SerialName("expense")
    val expense: ReportItem,
    @SerialName("income")
    val income: ReportItem,
    @SerialName("totalAmount")
    val totalAmount: Long
) {
    @Serializable
    data class ReportItem(
        @SerialName("categoryAmount")
        val categoryAmount: Long?,
        @SerialName("categoryId")
        val categoryId: Int?,
        @SerialName("totalAmount")
        val totalAmount: Int
    )
}