package dev.rezyfr.trackerr.presentation.theme

import androidx.compose.ui.graphics.Color
import dev.rezyfr.trackerr.domain.model.CategoryType

val Dark100 = Color(0xff0D0E0F)
val Dark75 = Color(0xff161719)
val Dark50 = Color(0xff212325)
val Dark25 = Color(0xff292B2D)

val Light100 = Color(0xffFFFFFF)
val Light80 = Color(0xffFCFCFC)
val Light60 = Color(0xffF1F1FA)
val Light20 = Color(0xff91919F)

val Violet100 = Color(0xff7F3DFF)
val Violet80 = Color(0xff8F57FF)
val Violet60 = Color(0xffB18AFF)
val Violet40 = Color(0xffD3BDFF)
val Violet20 = Color(0xffEEE5FF)

val Red100 = Color(0xffFD3C4A)
val Red20 = Color(0xffFDD5D7)

val Yellow20 = Color(0xFFFCEED4)

val Green100 = Color(0xff00A86B)

// Icon color
val BlueIcon = Color(0xff0077FF)
val RedIcon = Color(0xffFD3C4A)
val VioletIcon = Color(0xff7F3DFF)
val YellowIcon = Color(0xffFCAC12)
val GreenIcon = Color(0xff00A86B)
val TealIcon = Color(0xff26a69a)
val OrangeIcon = Color(0xffff5722)
val CyanIcon = Color(0xff29b6f6)
val Disabled = Color(0xffC6C6C6)

val HomeTopBackground = Color(0xFFFFF6E5)

val IconColors = listOf(BlueIcon, RedIcon, VioletIcon, YellowIcon, GreenIcon, TealIcon, OrangeIcon, CyanIcon)
fun String.color(): Color {
    var hex = this.removePrefix("#")
    if (hex.length == 6) {
        // Alpha not specified so append full opacity.
        hex = "FF${hex}"
    }

    check(hex.length == 8) {
        "Hex value has a length of ${hex.length} but a length of 6 or 8 is required without a # prefix OR a length of 7 or 9 characters with a # prefix."
    }

    return Color(hex.toLong(radix = 16))
}

fun CategoryType.typeIndicatorColor() = when (this) {
    CategoryType.EXPENSE -> Red100
    CategoryType.INCOME -> Green100
    else -> Green100
}