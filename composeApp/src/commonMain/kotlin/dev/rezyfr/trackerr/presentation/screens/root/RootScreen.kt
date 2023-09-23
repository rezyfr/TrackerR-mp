package dev.rezyfr.trackerr.presentation.screens.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.presentation.screens.auth.AuthComponent
import dev.rezyfr.trackerr.presentation.screens.create.account.AddAccountScreen
import dev.rezyfr.trackerr.presentation.screens.create.category.AddCategoryScreen
import dev.rezyfr.trackerr.presentation.screens.create.transaction.AddTransactionScreen
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.AddCategory
import dev.rezyfr.trackerr.presentation.screens.login.LoginScreen
import dev.rezyfr.trackerr.presentation.screens.main.MainComponent
import dev.rezyfr.trackerr.presentation.screens.main.MainMenu
import dev.rezyfr.trackerr.presentation.screens.onboarding.OnboardingScreen
import dev.rezyfr.trackerr.presentation.screens.register.RegisterScreen
import dev.rezyfr.trackerr.presentation.screens.reportwrap.ReportWrapScreen
import dev.rezyfr.trackerr.presentation.screens.start.StartScreen
import dev.rezyfr.trackerr.presentation.theme.Light20

@Composable
fun RootScreen(
    component: RootComponent,
) {
    val state = component.state.collectAsState()

    Children(component.childStack, animation = stackAnimation(slide())) {
        it.instance.let { child ->
            when (child) {
                is RootComponent.Child.Auth -> {
                    Children(child.authComponent.childStack, animation = stackAnimation(slide())) {
                        it.instance.let { authChild ->
                            when (authChild) {
                                is AuthComponent.Child.Login -> LoginScreen(authChild.loginComponent)
                                is AuthComponent.Child.Register -> RegisterScreen(authChild.registerComponent)
                                is AuthComponent.Child.Onboarding -> OnboardingScreen(authChild.onboardingComponent)
                                is AuthComponent.Child.Start -> StartScreen(authChild.startComponent)
                                is AuthComponent.Child.OnBoardingAddAccount -> AddAccountScreen(authChild.onboardingAddAccountComponent) {
                                    component.onAction(RootComponent.Action.NavigateToMain)
                                }
                            }
                        }
                    }
                }
                is RootComponent.Child.Main -> {
                    Children(child.mainComponent.child) { mainChild ->
                        mainChild.instance.let { mainComponentInstance ->
                            when (mainComponentInstance) {
                                is MainComponent.Tab.Home,
                                is MainComponent.Tab.Transaction -> MainMenu(child.mainComponent)
                                is MainComponent.Screen.AddTransaction -> AddTransactionScreen(mainComponentInstance.addTransactionComponent)
                                is MainComponent.Screen.AddCategory -> AddCategoryScreen(mainComponentInstance.addCategoryComponent)
                                is MainComponent.Screen.ReportWrap -> ReportWrapScreen(mainComponentInstance.reportWrapComponent)
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.value.tokenResult is UiResult.Loading) {
        Box(Modifier.fillMaxSize().background(Light20.copy(alpha = 0.3f))) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else if (state.value.tokenResult is UiResult.Success) {
        component.onAction(RootComponent.Action.NavigateToMain)
    }
}