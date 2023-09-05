package dev.rezyfr.trackerr

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import dev.rezyfr.trackerr.presentation.screens.RootScreen
import dev.rezyfr.trackerr.presentation.screens.login.LoginScreen
import dev.rezyfr.trackerr.presentation.theme.AppTheme
import dev.rezyfr.trackerr.utils.SlideTransition

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    AppTheme {
        Navigator(
            screen = LoginScreen(),
        ) {
            SlideTransition(it) { screen ->
                screen.Content()
            }
        }
    }
}