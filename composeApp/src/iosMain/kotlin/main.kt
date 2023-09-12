import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.rezyfr.trackerr.App
import dev.rezyfr.trackerr.di.initKoin
import dev.rezyfr.trackerr.presentation.screens.RootComponent
import platform.UIKit.UIViewController

fun MainViewController(
    lifecycle: LifecycleRegistry,
    ): UIViewController {
    initKoin(enableNetworkLogs = true)
    val rootComponent =
        RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
        )
    return ComposeUIViewController { App(rootComponent) }
}
