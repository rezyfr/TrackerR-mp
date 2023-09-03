package dev.rezyfr.trackerr

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import dev.rezyfr.trackerr.presentation.screens.create.account.AddAccountScreen
import dev.rezyfr.trackerr.presentation.screens.login.LoginScreen
import dev.rezyfr.trackerr.presentation.screens.onboarding.OnboardingScreen
import dev.rezyfr.trackerr.presentation.screens.start.StartScreen
//import dev.rezyfr.trackerr.presentation.screens.start.StartScreen
import dev.rezyfr.trackerr.presentation.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Navigator(
            screen = LoginScreen(),
        )
    }
}