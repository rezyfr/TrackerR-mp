package dev.rezyfr.trackerr.presentation.component.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

fun Int.getShortMonths() : String {
    return when(this) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 ->"Oct"
        11 -> "Nov"
        12  -> "Dec"
        else -> throw IllegalArgumentException("Month must be between 1 and 12")
    }
}

fun Int.getFullMonths() : String {
    return when(this) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> throw IllegalArgumentException("Month must be between 1 and 12")
    }
}

fun getCurrentLdt() : LocalDateTime {
    val now: Instant = Clock.System.now()
    return now.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun getCurrentLd() : LocalDate {
    return Clock.System.todayIn(TimeZone.currentSystemDefault())
}