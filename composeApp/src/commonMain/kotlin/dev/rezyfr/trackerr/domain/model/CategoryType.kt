package dev.rezyfr.trackerr.domain.model

import kotlinx.serialization.SerialName

enum class CategoryType(val label: String) {
    @SerialName("income")
    INCOME("Income"),
    @SerialName("expense")
    EXPENSE("Expense");

    companion object {
        fun fromString(value: String): CategoryType {
            return when (value) {
                "INCOME",  "income", "Income"-> INCOME
                "EXPENSE", "expense", "Expense" -> EXPENSE
                else -> throw IllegalArgumentException()
            }
        }
    }
}