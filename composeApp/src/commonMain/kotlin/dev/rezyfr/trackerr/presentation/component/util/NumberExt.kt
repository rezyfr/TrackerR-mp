package dev.rezyfr.trackerr.presentation.component.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Number?.format(
    pattern: NumberPattern = NumberPattern.NO_DECIMAL,
    roundingMode: RoundingMode = RoundingMode.HALF_UP,
    maxFractionDigit: Int? = null,
    isNeedDefaultBlank: Boolean = false
): String {
    if (this == null || (this is Double && isNaN())) return if (!isNeedDefaultBlank) "-" else ""
    val symbol = DecimalFormatSymbols(Locale.getDefault())
    symbol.decimalSeparator = '.'
    symbol.groupingSeparator = ','
    val decimalFormat = DecimalFormat(pattern.value, symbol)
    maxFractionDigit?.let {
        decimalFormat.maximumFractionDigits = it
    }
    decimalFormat.isGroupingUsed = true
    decimalFormat.groupingSize = 3
    if (roundingMode != RoundingMode.UNNECESSARY) {
        decimalFormat.roundingMode = roundingMode
    }
    val results = decimalFormat.format(this)
    return if (results.endsWith(".00")) {
        results.dropLast(3)
    } else {
        results.format(this)
    }
}


enum class NumberPattern(val value: String) {
    NO_DECIMAL("###,###,###"),
    ONE_DECIMAL("###,###,###.#"),
    TWO_DECIMAL("###,###,###.##"),
    THREE_DECIMAL("###,###,###.###"),
    FOUR_DECIMAL("###,###,###.####"),
    FOUR_DECIMAL_FIXED("###,###,##0.0000"),
    TWO_DECIMAL_FIXED_1("#.0#"),
    FIXED_NUMBER_AND_DECIMAL("###,###,##0.00")
}
