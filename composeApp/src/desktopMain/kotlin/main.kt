import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.rezyfr.trackerr.App
import dev.rezyfr.trackerr.di.initKoin
import dev.rezyfr.trackerr.presentation.screens.login.LoginScreen

fun main() = application {
    initKoin(enableNetworkLogs = true)
    Window(
        title = "TrackerR Multiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        App() }
}