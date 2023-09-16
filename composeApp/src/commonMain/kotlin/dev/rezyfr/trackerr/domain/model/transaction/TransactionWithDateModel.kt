package dev.rezyfr.trackerr.domain.model.transaction

data class TransactionWithDateModel(
    val date: String,
    val transactions: List<TransactionModel>
)
