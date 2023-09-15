package dev.rezyfr.trackerr.domain.model

enum class Granularity(val label: String) {
    WEEK("Week"), MONTH("Month"), YEAR("Year");

    companion object {
        fun fromString(value: String): Granularity {
            return when (value) {
                "WEEK",  "week", "Week"-> WEEK
                "MONTH", "month", "Month" -> MONTH
                "YEAR", "year", "Year" -> YEAR
                else -> throw IllegalArgumentException()
            }
        }
    }
}