package dev.rezyfr.trackerr

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO

actual val interFontFamily: FontFamily
    get() = FontFamily(
        fonts = listOf(
            Font(
                resId = R.font.inter_regular,
                weight = FontWeight.Normal,
            ),
            Font(
                resId = R.font.inter_medium,
                weight = FontWeight.Medium
            ),
            Font(
                resId = R.font.inter_semibold,
                weight = FontWeight.SemiBold
            ),
            Font(
                resId = R.font.inter_bold,
                weight = FontWeight.Bold
            )
        )
    )