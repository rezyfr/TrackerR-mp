package dev.rezyfr.trackerr.domain.model.transaction


data class TransactionFrequencyModel(
    val xAxisData: List<String>,
    val expenseData: List<Double>,
    val incomeData: List<Double>
)