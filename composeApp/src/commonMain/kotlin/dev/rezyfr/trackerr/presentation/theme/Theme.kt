package dev.rezyfr.trackerr.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Light default theme color scheme
 */
private val LightDefaultColorScheme = lightColorScheme(
    primary = Violet100,
    onPrimary = Light80,
    primaryContainer = Violet20,
    onPrimaryContainer = Violet100,
    error = Red100,
    onError = Light80,
    errorContainer = Red20,
    onErrorContainer = Red100,
    background = Light100,
    onBackground = Dark50,
    surface = Light100,
    onSurface = Dark50,
    outline = Light60,
    tertiary = Light20
)

/**
 * Dark default theme color scheme
 */
//private val DarkDefaultColorScheme = darkColorScheme(
//    primary = Blue40,
//    onPrimary = Blue10,
//    primaryContainer = Blue30,
//    onPrimaryContainer = Blue90,
//    secondary = Orange80,
//    onSecondary = Orange20,
//    secondaryContainer = Orange30,
//    onSecondaryContainer = Orange90,
//    error = Red80,
//    onError = Red20,
//    errorContainer = Red30,
//    onErrorContainer = Red90,
//    background = DarkBlue10,
//    onBackground = DarkBlue90,
//    surface = DarkBlue10,
//    onSurface = DarkBlue90,
//    outline = DarkBlue80
//)

val MainTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 64.sp,
        lineHeight = 80.sp,
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 34.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 22.sp,
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 19.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 12.sp
    )
)

@Composable
internal fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val backgroundScheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp
    )
    CompositionLocalProvider(LocalBackgroundTheme provides backgroundScheme) {
        MaterialTheme(
            colorScheme = LightDefaultColorScheme,
            typography = MainTypography,
            content = content
        )
    }
}
