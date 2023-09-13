import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dev.rezyfr.trackerr.App
import dev.rezyfr.trackerr.di.initKoin
import dev.rezyfr.trackerr.presentation.screens.root.RootComponent
import platform.UIKit.UIViewController

fun MainViewController(
    lifecycle: LifecycleRegistry,
    ): UIViewController {
    initKoin(enableNetworkLogs = true)
    val rootComponent =
        RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            storeFactory = DefaultStoreFactory()
        )
    return ComposeUIViewController { App(rootComponent) }
}
