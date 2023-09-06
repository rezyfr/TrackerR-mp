package dev.rezyfr.trackerr.presentation.component.util

expect fun Number?.format(
    pattern: NumberPattern = NumberPattern.NO_DECIMAL,
    maxFractionDigit: Int? = null,
    isNeedDefaultBlank: Boolean = false
): String


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
