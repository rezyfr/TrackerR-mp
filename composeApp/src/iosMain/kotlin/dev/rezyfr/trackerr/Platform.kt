package dev.rezyfr.trackerr

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO
actual val mainDispatcher: CoroutineDispatcher
    get() = Dispatchers.Main

actual val interFontFamily: FontFamily = FontFamily(
    Typeface(loadCustomFont("inter"))
)