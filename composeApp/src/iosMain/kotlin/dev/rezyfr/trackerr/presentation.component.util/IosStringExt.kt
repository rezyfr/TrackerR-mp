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
//let formatter = Foundation.DateFormatter()
//formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" //2017-04-01T18:05:00.000
//let date1  = formatter.date(from: "2017-04-01T18:05:00.000Z")
//print("date:\(String(describing: date1))")
//formatter.dateFormat = "HH:mm"
//let resultTime = formatter.string(from: date1!)
//print("time:\(String(describing: resultTime))")