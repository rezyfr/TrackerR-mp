package dev.rezyfr.trackerr.presentation.screens.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class RegisterScreen(
//    val viewModel: RegisterViewModel = koinInject(),
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
//        val loginState by viewModel.uiState.collectAsState()

        RegisterScreen (
            onRegister = {  },
            onStoreUser = {  },
        )
    }
}

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    onStoreUser: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        SignInButton(
            onRegister = onRegister
        )
//        when (state) {
//            is RegisterUiState.Loading -> {
//
//            }
//            is RegisterUiState.Error -> {
//                Text("Error: ${state.throwable.message}")
//            }
//            is RegisterUiState.Success -> {
//                onStoreUser.invoke()
//            }
//        }
    }
}

@Composable
fun SignInButton(
    onRegister: () -> Unit = { },
) {
    Button(
        onClick = {

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = "Register", modifier = Modifier.padding(6.dp))
    }
}