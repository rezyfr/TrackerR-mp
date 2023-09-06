package dev.rezyfr.trackerr.presentation.component.util

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle


actual fun Number?.format(
    pattern: NumberPattern,
    maxFractionDigit: Int?,
    isNeedDefaultBlank: Boolean
): String {
    NSNumberFormatter().apply {
        this.numberStyle = NSNumberFormatterDecimalStyle
        this.maximumFractionDigits = maxFractionDigit?.toULong() ?: 0u
        this.groupingSize = 3u
        this.groupingSeparator = ","
        this.decimalSeparator = "."
    }.let { formatter ->
        return if (this == null || (this is Double && isNaN())) {
            if (!isNeedDefaultBlank) "-" else ""
        } else {
            formatter.stringFromNumber(NSNumber(long = this.toLong())) ?: ""
        }
    }
}