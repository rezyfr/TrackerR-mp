package dev.rezyfr.trackerr.screens.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinInject(),
//    navigator: AuthNavigator,
) {
    val loginState by viewModel.uiState.collectAsState()

    LoginScreen (
        state = loginState,
        onLogin = viewModel::storeUserData,
        onStoreUser = {  },
    )
}

@Composable
fun LoginScreen(
    state: LoginUiState,
    onLogin: (Unit) -> Unit,
    onStoreUser: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        SignInButton(
            onLogin = onLogin
        )
        when (state) {
            is LoginUiState.Loading -> {

            }
            is LoginUiState.Error -> {
                Text("Error: ${state.throwable.message}")
            }
            is LoginUiState.Success -> {
                onStoreUser.invoke()
            }
        }
    }
}

@Composable
fun SignInButton(
    onLogin: (Unit) -> Unit = { },
) {
//    val context = LocalContext.current
//    val authResultLauncher =
//        rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
//            try {
//                val gsa = task?.getResult(ApiException::class.java)
//                if (gsa != null) {
//                    onLogin.invoke(gsa)
//                }
//            } catch (e: ApiException) {
//                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
    Button(
        onClick = {
//            authResultLauncher.launch(1)
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
        Text(text = "Sign in with Google", modifier = Modifier.padding(6.dp))
    }
}