package dev.rezyfr.trackerr.presentation.screens.start

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rezyfr.trackerr.Res.image
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.ButtonText
import dev.rezyfr.trackerr.presentation.component.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.component.TrSecondaryButton
import dev.rezyfr.trackerr.presentation.screens.login.LoginScreen
import dev.rezyfr.trackerr.presentation.screens.register.RegisterScreen
import dev.rezyfr.trackerr.presentation.theme.Light20

class StartScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            bottomBar = {
                ButtonSection(
                    goToRegister = { navigator.push(RegisterScreen()) },
                    goToLogin = { navigator.push(LoginScreen()) }
                )
            }
        ) {
            StartScreenContent(
                modifier = Modifier.padding(it),
            )
        }
    }

    @Composable
    private fun StartScreenContent(
        modifier: Modifier = Modifier,
    ) {
        VSpacer(32)
        StartPager(modifier)
    }
    @Composable
    private fun ButtonSection(
        goToRegister: () -> Unit,
        goToLogin: () -> Unit
    ) {
        Column(
            Modifier.fillMaxWidth()
                .padding(20.dp)
        ) {
            RegisterButton { goToRegister.invoke() }
            VSpacer(16)
            LoginButton { goToLogin.invoke() }
        }
    }
    @Composable
    private fun RegisterButton(
        goToRegister: () -> Unit
    ) {
        TrPrimaryButton(
            text = { ButtonText("Sign Up") },
            onClick = goToRegister,
            modifier = Modifier.fillMaxWidth()
        )
    }
    @Composable
    private fun LoginButton(
        goToLogin: () -> Unit
    ) {
        TrSecondaryButton(
            text = "Login",
            onClick = goToLogin,
            modifier = Modifier.fillMaxWidth()
        )
    }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun PagerIndicator(
        pageCount: Int = 0,
        pagerState: PagerState
    ) {
        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
                val size = if (pagerState.currentPage == iteration) 20.dp else 10.dp
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(size)
                )
            }
        }
    }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun StartPager(modifier: Modifier = Modifier) {
        val pageCount = 3
        val pagerState = rememberPagerState(
            pageCount = { 3 },
        )
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.wrapContentHeight()
            ) { page ->
                StartPagerContent(page)
            }
            Spacer(Modifier.weight(1f))
            PagerIndicator(pageCount, pagerState)
        }
    }
    @Composable
    private fun StartPagerContent(
        page: Int
    ) {
        val (image, title, description) = when (page) {
            0 -> {
                Triple(
                    image.img_start_1,
                    "Gain total control of your money",
                    "Become your own money manager and make every cent count",
                )
            }

            1 -> {
                Triple(
                    image.img_start_2,
                    "Know where your money goes",
                    "Track your transaction easily, with categories and financial report ",
                )
            }

            else -> {
                Triple(
                    image.img_start_3,
                    "Plan your budget",
                    "Plan your budget and save money for the things you want",
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
            )
            VSpacer(32)
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center,
            )
            VSpacer(16)
            Text(
                text = description,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Light20
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}