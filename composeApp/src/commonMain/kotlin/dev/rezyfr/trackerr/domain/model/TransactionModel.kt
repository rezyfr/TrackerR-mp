package dev.rezyfr.trackerr.domain.model

data class TransactionModel(
    val id: Int,
    val desc: String,
    val amount: Long,
    val type: String,
    val date: String,
    val category: String,
    val categoryIcon: String,
)
