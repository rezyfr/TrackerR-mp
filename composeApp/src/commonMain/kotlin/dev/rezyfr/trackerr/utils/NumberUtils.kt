package dev.rezyfr.trackerr.utils

object NumberUtils {
    fun getCleanString(s: String): Double {
        val cleanString = s.replace("[,.]".toRegex(), "")
        return try {
            val parsed = cleanString.toDouble()
            parsed
        } catch (e: Exception) {
            0.0
        }
    }
}