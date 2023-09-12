package dev.rezyfr.trackerr

import androidx.compose.ui.text.font.FontFamily
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.skia.Typeface

expect val ioDispatcher: CoroutineDispatcher
expect val mainDispatcher: CoroutineDispatcher

expect val interFontFamily: FontFamily

fun loadCustomFont(name: String): Typeface {
    return Typeface.makeFromName(name, org.jetbrains.skia.FontStyle.NORMAL)
}