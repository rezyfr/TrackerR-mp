import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.rezyfr.trackerr.App
import dev.rezyfr.trackerr.di.initKoin
import dev.rezyfr.trackerr.screens.auth.AuthScreen

fun main() = application {
    initKoin(baseUrl = "https://api.themoviedb.org/3/", enableNetworkLogs = true)
    Window(
        title = "TrackerR Multiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) { AuthScreen() }
}