package dev.rezyfr.trackerr


import androidx.compose.runtime.Composable
import dev.rezyfr.trackerr.presentation.screens.root.RootComponent
import dev.rezyfr.trackerr.presentation.screens.root.RootScreen
import dev.rezyfr.trackerr.presentation.theme.AppTheme

@Composable
fun App(
    component: RootComponent,
) {
    AppTheme {
        RootScreen(component)
    }
}
