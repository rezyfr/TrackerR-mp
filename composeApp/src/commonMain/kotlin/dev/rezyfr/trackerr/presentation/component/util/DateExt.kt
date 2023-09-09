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
        0 -> "Jan"
        1 -> "Feb"
        2 -> "Mar"
        3 -> "Apr"
        4 -> "May"
        5 -> "Jun"
        6 -> "Jul"
        7 -> "Aug"
        8 -> "Sep"
        9 -> "Oct"
        10 -> "Nov"
        11 -> "Dec"
        else -> throw IllegalArgumentException("Month must be between 0 and 11")
    }
}

fun Int.getFullMonths() : String {
    return when(this) {
        0 -> "January"
        1 -> "February"
        2 -> "March"
        3 -> "April"
        4 -> "May"
        5 -> "June"
        6 -> "July"
        7 -> "August"
        8 -> "September"
        9 -> "October"
        10 -> "November"
        11 -> "December"
        else -> throw IllegalArgumentException("Month must be between 0 and 11")
    }
}

fun getCurrentLdt() : LocalDateTime {
    val now: Instant = Clock.System.now()
    return now.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun getCurrentLd() : LocalDate {
    return Clock.System.todayIn(TimeZone.currentSystemDefault())
}