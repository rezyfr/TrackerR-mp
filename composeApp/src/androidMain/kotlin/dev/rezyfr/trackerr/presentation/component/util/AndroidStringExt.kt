package dev.rezyfr.trackerr.presentation.component.util

import java.text.SimpleDateFormat
import java.util.Locale

actual fun String.toDateFormat(currentFormat: String, newFormat: String): String {
    return try {
        val dateCurrentFormat = SimpleDateFormat(currentFormat, Locale.getDefault()).parse(this)
        SimpleDateFormat(newFormat, Locale.getDefault()).format(dateCurrentFormat!!)
    } catch (e: Exception) {
        this
    }
}