package dev.rezyfr.trackerr

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dev.rezyfr.trackerr.di.initKoin
import dev.rezyfr.trackerr.presentation.screens.root.RootComponent
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class AndroidApp : Application() {

    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initKoin(enableNetworkLogs = true) {
            androidLogger()
            androidContext(this@AndroidApp)
        }
        Napier.base(DebugAntilog())
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent =
            RootComponent(
                componentContext = defaultComponentContext(),
                storeFactory = DefaultStoreFactory()
            )
        setContent { App(rootComponent) }
    }
}