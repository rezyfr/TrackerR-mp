package dev.rezyfr.trackerr.domain.model.transaction

import dev.rezyfr.trackerr.domain.model.CategoryModel

data class TransactionReportModel(
    val expense: ReportItem,
    val income: ReportItem,
    val totalAmount: Long
) {
    data class ReportItem(
        val categoryAmount: Long?,
        val category: CategoryModel?,
        val totalAmount: Int
    )
}