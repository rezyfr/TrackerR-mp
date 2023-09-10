package dev.rezyfr.trackerr.presentation.component.util;

import platform.Foundation.*

actual fun String.toDateFormat(
    currentFormat: String, newFormat: String
): String {

    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = currentFormat
    val currentDate = dateFormatter.dateFromString(this)

    dateFormatter.dateFormat = newFormat
    return dateFormatter.stringFromDate(currentDate!!)
}