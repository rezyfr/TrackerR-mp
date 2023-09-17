package dev.rezyfr.trackerr.utils

import dev.rezyfr.trackerr.presentation.component.util.SERVER_DATE_FORMAT
import dev.rezyfr.trackerr.presentation.component.util.toDateFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun String.toDateRelatives(currentFormat: String = SERVER_DATE_FORMAT, newFormat: String): String {
    return try {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val date = LocalDateTime.parse(this)
        if (date.dayOfMonth == today.dayOfMonth &&
            date.monthNumber == today.monthNumber &&
            date.year == today.year
        ) "Today"
        else if (date.dayOfMonth == today.dayOfMonth - 1 && date.monthNumber == today.monthNumber && date.year == today.year) "Yesterday"
        else this.toDateFormat(currentFormat, newFormat)
    } catch (e: Exception) {
        this
    }
}