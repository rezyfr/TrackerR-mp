package dev.rezyfr.trackerr.presentation.screens.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rezyfr.trackerr.presentation.component.TrButton
import dev.rezyfr.trackerr.presentation.component.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.component.TrTextField
import dev.rezyfr.trackerr.presentation.screens.register.RegisterScreen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginScreen() : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val viewModel: LoginViewModel by inject()
        val navigator = LocalNavigator.currentOrThrow
        val loginState by viewModel.uiState.collectAsState()
        LoginScreen(
            state = loginState,
            onLogin = {
//                navigator.push(RegisterScreen())
                viewModel.login("user2@gmail.com", "123456")
            },
            onChangeEmail = { viewModel.onEmailChange(it) },
            onChangePassword = { viewModel.onPasswordChange(it) },
            modifier = Modifier.fillMaxSize()
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginUiState,
    onLogin: () -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Login", style = MaterialTheme.typography.titleSmall) },
                navigationIcon = { Icon(Icons.Default.ArrowBack, null, modifier = Modifier.padding(start = 16.dp)) },
                actions = { },
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
    ) {
        LoginContent(
            state = state,
            onLogin = onLogin,
            modifier = Modifier.padding(it),
            onChangeEmail = onChangeEmail,
            onChangePassword = onChangePassword
        )
    }
}
@Composable
fun LoginContent(
    state: LoginUiState,
    onLogin: () -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    modifier: Modifier
) {
    Column(modifier) {
        Spacer(Modifier.height(36.dp))
        LoginEmailField(
            email = state.email,
            onChangeEmail = { onChangeEmail(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        Spacer(Modifier.height(24.dp))
        LoginEmailField(
            email = state.email,
            onChangeEmail = { onChangeEmail(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        Spacer(Modifier.height(40.dp))
        LoginButton(
            onLogin = onLogin
        )
    }
}
@Composable
fun LoginEmailField(
    modifier: Modifier = Modifier,
    onChangeEmail: (String) -> Unit = {},
    email: String
) {
    TrTextField(
        modifier = modifier,
        value = email,
        onValueChange = onChangeEmail,
        placeholder = "Email"
    )
}

@Composable
fun LoginButton(
    onLogin: () -> Unit = { },
) {
    TrPrimaryButton(
        onClick = onLogin,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        text = "Login"
    )
}