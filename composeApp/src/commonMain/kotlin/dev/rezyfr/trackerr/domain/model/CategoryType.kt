package dev.rezyfr.trackerr.domain.model

enum class CategoryType {
    INCOME,
    EXPENSE;

    companion object {
        fun fromString(value: String): CategoryType {
            return when (value) {
                "INCOME",  "income"-> INCOME
                "EXPENSE", "expense" -> EXPENSE
                else -> throw IllegalArgumentException()
            }
        }
    }
}