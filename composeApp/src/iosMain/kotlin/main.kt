import androidx.compose.ui.window.ComposeUIViewController
import dev.rezyfr.trackerr.App
import dev.rezyfr.trackerr.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    initKoin(enableNetworkLogs = true)

    return ComposeUIViewController { App() }
}
