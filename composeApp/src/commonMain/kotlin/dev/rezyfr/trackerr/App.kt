package dev.rezyfr.trackerr


import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import dev.rezyfr.trackerr.presentation.RootComponent
import dev.rezyfr.trackerr.presentation.screens.auth.AuthComponent
import dev.rezyfr.trackerr.presentation.screens.login.LoginScreen
import dev.rezyfr.trackerr.presentation.screens.main.MainComponent
import dev.rezyfr.trackerr.presentation.screens.main.MainScreen
import dev.rezyfr.trackerr.presentation.screens.register.RegisterScreen
import dev.rezyfr.trackerr.presentation.screens.root.RootStore
import dev.rezyfr.trackerr.presentation.theme.AppTheme

@Composable
fun App(
    component: RootComponent,
) {
    AppTheme {
        RootContent(component)
    }
}

@Composable
fun RootContent(
    component: RootComponent,
) {
    Children(component.childStack, animation = stackAnimation(slide())) {
        it.instance.let { child ->
            when (child) {
                is RootComponent.Child.Auth -> {
                    Children(child.authComponent.childStack, animation = stackAnimation(slide())) {
                        it.instance.let { authChild ->
                            when (authChild) {
                                is AuthComponent.Child.Login -> LoginScreen(authChild.loginComponent) {
                                    component.goToMain()
                                }
                                is AuthComponent.Child.Register -> RegisterScreen(authChild.registerComponent)
                            }
                        }
                    }
                }
                is RootComponent.Child.Main -> {
                    MainScreen(child.mainComponent)
                }
            }
        }
    }
}