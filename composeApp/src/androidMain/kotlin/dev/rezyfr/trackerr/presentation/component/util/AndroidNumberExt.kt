package dev.rezyfr.trackerr.presentation.component.util


import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

actual fun Number?.format(
    pattern: NumberPattern,
    maxFractionDigit: Int?,
    isNeedDefaultBlank: Boolean
): String {
    if (this == null || (this is Double && isNaN())) return if (!isNeedDefaultBlank) "-" else ""
    val symbol = DecimalFormatSymbols(Locale.getDefault())
    symbol.decimalSeparator = ','
    symbol.groupingSeparator = '.'
    val decimalFormat = DecimalFormat(pattern.value, symbol)
    maxFractionDigit?.let {
        decimalFormat.maximumFractionDigits = it
    }
    decimalFormat.isGroupingUsed = true
    decimalFormat.groupingSize = 3

    val results = decimalFormat.format(this)
    return if (results.endsWith(".00")) {
        results.dropLast(3)
    } else {
        results.format(this)
    }
}
