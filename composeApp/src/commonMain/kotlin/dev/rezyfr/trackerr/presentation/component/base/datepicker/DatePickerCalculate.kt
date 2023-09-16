package dev.rezyfr.trackerr.presentation.component.base.datepicker

import dev.rezyfr.trackerr.presentation.component.util.getFullMonths
import dev.rezyfr.trackerr.presentation.component.util.getShortMonths
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
import kotlinx.datetime.todayIn

interface DateProperty {
    val text: String
    val value: Int
    val index: Int
}

data class DayOfMonth(
    override val text: String,
    override val index: Int,
    override val value: Int
) : DateProperty

data class Month(
    override val index: Int,
    override val value: Int,
    override val text: String
) : DateProperty

data class Year(
    override val value: Int,
    override val text: String,
    override val index: Int
) : DateProperty

internal fun calculateDayOfMonths(): List<DateProperty> {
    val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
    return calculateDayOfMonths(now.monthNumber, now.year)
}

internal fun calculateDayOfMonths(
    month: Int, year: Int
): List<DateProperty> {
    val isLeapYear = !(year % 100 == 0 && year % 400 != 0)

    val month31day = (1..31).map {
        DayOfMonth(
            text = it.toString(),
            value = it,
            index = it - 1
        )
    }
    val month30day = (1..30).map {
        DayOfMonth(
            text = it.toString(),
            value = it,
            index = it - 1
        )
    }
    val month29day = (1..29).map {
        DayOfMonth(
            text = it.toString(),
            value = it,
            index = it - 1
        )
    }

    return when (month) {
        1 -> {
            month31day
        }

        2 -> {
            month29day.let {
                if (isLeapYear) {
                    it.toMutableList().apply {
                        removeLast()
                    }
                } else it
            }
        }

        3 -> {
            month31day
        }

        4 -> {
            month30day
        }

        5 -> {
            month31day
        }

        6 -> {
            month30day
        }

        7 -> {
            month31day
        }

        8 -> {
            month31day
        }

        9 -> {
            month30day
        }

        10 -> {
            month31day
        }

        11 -> {
            month30day
        }

        12 -> {
            month31day
        }

        else -> {
            emptyList()
        }
    }
}

internal fun calculateMonths(useShortDate: Boolean = false): List<Month> {
    return (1..12).map {
        Month(
            text = if (useShortDate) it.getShortMonths()
            else it.getFullMonths(),
            value = it,
            index = it
        )
    }
}

internal fun calculateYears(
    yearsRange: IntRange = IntRange(2000, 2100),
): List<DateProperty> {
    return yearsRange.map {
        Year(
            text = it.toString(),
            value = it,
            index = yearsRange.indexOf(it)
        )
    }
}

fun LocalDateTime.toDayOfMonth() : DayOfMonth {
    return DayOfMonth(
        text = dayOfMonth.toString(),
        value = dayOfMonth,
        index = dayOfMonth - 1
    )
}

fun LocalDateTime.toMonth() : Month {
    return Month(
        text = monthNumber.getFullMonths(),
        value = monthNumber,
        index = monthNumber
    )
}

fun LocalDateTime.toYear(range: IntRange) : Year {
    return Year(
        text = year.toString(),
        value = year,
        index = range.indexOf(year)
    )
}