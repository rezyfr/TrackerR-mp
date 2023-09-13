package dev.rezyfr.trackerr

import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO
actual val mainDispatcher: CoroutineDispatcher
    get() = Dispatchers.Main
actual val interFontFamily: FontFamily
    get() = FontFamily(
        fonts = listOf(
            Font(
                resource = "inter_regular.ttf",
                weight = FontWeight.Normal,
            ),
            Font(
                resource = "inter_medium.ttf",
                weight = FontWeight.Medium
            ),
            Font(
                resource = "inter_semibold.ttf",
                weight = FontWeight.SemiBold
            ),
            Font(
                resource = "inter_bold.ttf",
                weight = FontWeight.Bold
            )
        )
    )