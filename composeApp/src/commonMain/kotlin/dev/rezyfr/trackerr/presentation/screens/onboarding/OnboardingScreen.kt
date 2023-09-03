package dev.rezyfr.trackerr.presentation.screens.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.ButtonText
import dev.rezyfr.trackerr.presentation.component.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.screens.create.account.AddAccountScreen

class OnboardingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        OnboardingScreen(
            onNext = {
                navigator.push(AddAccountScreen())
            }
        )
    }

    @Composable
    fun OnboardingScreen(
        onNext: () -> Unit
    ) {
        Scaffold(
            bottomBar = {
                TrPrimaryButton(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 50.dp),
                    text = { ButtonText("Let's go") },
                    onClick = onNext
                )
            }
        ) {
            Column(
                Modifier.padding(
                    bottom = it.calculateBottomPadding(),
                    top = 36.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            ) {
                Text(
                    "Let's setup your\naccount!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
                )
                VSpacer(32)
                Text(
                    "Account can be your bank, credit card, or your wallet",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}