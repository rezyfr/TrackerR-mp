import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dev.rezyfr.trackerr.App
import dev.rezyfr.trackerr.di.initKoin
import dev.rezyfr.trackerr.presentation.screens.root.RootComponent
import javax.swing.SwingUtilities

fun main() = application {
    initKoin(enableNetworkLogs = true)

    val lifecycle = LifecycleRegistry()

    val rootComponent = RootComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        storeFactory = DefaultStoreFactory()
    )
    Window(
        title = "TrackerR Multiplatform",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        App(rootComponent, true) }
}

fun <T> invokeOnAwtSync(block: () -> T): T {
    var result: T? = null
    SwingUtilities.invokeAndWait { result = block() }

    @Suppress("UNCHECKED_CAST")
    return result as T
}