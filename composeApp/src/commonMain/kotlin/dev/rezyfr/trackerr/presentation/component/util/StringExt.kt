package dev.rezyfr.trackerr.presentation.component.util


const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"

expect fun String.toDateFormat(currentFormat: String = SERVER_DATE_FORMAT, newFormat: String): String