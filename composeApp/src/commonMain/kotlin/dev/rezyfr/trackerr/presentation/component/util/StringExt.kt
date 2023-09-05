package dev.rezyfr.trackerr.presentation.component.util

import java.text.SimpleDateFormat
import java.util.Locale

const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm"
fun String.toDateFormat(currentFormat: String = SERVER_DATE_FORMAT, newFormat: String): String {
    return try {
        val dateCurrentFormat = SimpleDateFormat(currentFormat, Locale.getDefault()).parse(this)
        SimpleDateFormat(newFormat, Locale.getDefault()).format(dateCurrentFormat)
    } catch (e: Exception) {
        this
    }
}