package dev.rezyfr.trackerr

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import dev.rezyfr.trackerr.presentation.screens.RootComponent
import dev.rezyfr.trackerr.presentation.screens.auth.AuthComponent
import dev.rezyfr.trackerr.presentation.screens.login.LoginScreen
import dev.rezyfr.trackerr.presentation.screens.register.RegisterScreen
import dev.rezyfr.trackerr.presentation.theme.AppTheme

@OptIn(ExperimentalAnimationApi::class)
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
    Children(component.childStack) {
        it.instance.let { child ->
            when (child) {
                is RootComponent.Child.Auth -> {
                    Children(child.authComponent.childStack, animation = stackAnimation(slide())) {
                        it.instance.let { authChild ->
                            when (authChild) {
                                is AuthComponent.Child.Login -> LoginScreen(authChild.loginComponent)
                                is AuthComponent.Child.Register -> RegisterScreen(authChild.registerComponent)
                            }
                        }
                    }
                }
                is RootComponent.Child.Main -> TODO()
            }
        }
    }
}