package dev.rezyfr.trackerr.presentation.screens.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.base.ButtonText
import dev.rezyfr.trackerr.presentation.component.base.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.component.base.TrTextField
import dev.rezyfr.trackerr.presentation.component.base.TrTopBar
import dev.rezyfr.trackerr.presentation.screens.login.LoginComponent
import dev.rezyfr.trackerr.presentation.screens.login.LoginScreen
import dev.rezyfr.trackerr.presentation.screens.login.store.LoginStore
import dev.rezyfr.trackerr.presentation.screens.register.store.RegisterStore
import dev.rezyfr.trackerr.presentation.theme.Light20
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
fun RegisterScreen(
    registerComponent: RegisterComponent
) {
//    val navigator = LocalNavigator.currentOrThrow
    val registerState by registerComponent.state.collectAsState()

//    LaunchedEffect(registerState.registerResult) {
//        if (registerState.registerResult is UiResult.Success) {
//            navigator.replaceAll(LoginScreen())
//        }
//    }

    RegisterScreen(
        state = registerState,
        onEvent = registerComponent::onEvent,
        onAction = registerComponent::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    state: RegisterStore.State,
    onEvent: (RegisterStore.Intent) -> Unit,
    onAction: (RegisterComponent.Action) -> Unit,
) {
    Scaffold(
        topBar = {
            TrTopBar(text = "Sign Up", onBackPressed = { onAction(RegisterComponent.Action.NavigateBack) })
        }
    ) {
        RegisterContent(
            state = state,
            onEvent = onEvent,
            onAction = onAction,
            modifier = Modifier.padding(it),
        )
    }
}

@Composable
fun RegisterContent(
    state: RegisterStore.State,
    onEvent: (RegisterStore.Intent) -> Unit,
    onAction: (RegisterComponent.Action) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VSpacer(36)
        TrTextField(
            value = state.name,
            onValueChange = { onEvent(RegisterStore.Intent.OnNameChange(it)) },
            placeholder = "Name",
        )
        VSpacer(24)
        TrTextField(
            value = state.email,
            onValueChange = { onEvent(RegisterStore.Intent.OnEmailChange(it)) },
            placeholder = "Email",
        )
        VSpacer(24)
        PasswordField(
            password = state.password,
            onChangePassword = { onEvent(RegisterStore.Intent.OnPasswordChange(it)) },
        )
        VSpacer(36)
        SignUpButton(
            registerResult = state.registerResult,
            onClick = { onEvent(RegisterStore.Intent.Register) }
        )
        VSpacer(32)
        HaveAccountText(
            goToLogin = { onAction(RegisterComponent.Action.NavigateToLogin) }
        )
    }
}

@Composable
fun PasswordField(
    password: String,
    onChangePassword: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    TrTextField(
        value = password,
        onValueChange = onChangePassword,
        placeholder = "Password",
        modifier = modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                Icon(
                    imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    null
                )
            }
        }
    )
}

@Composable
fun SignUpButton(
    registerResult: UiResult<Unit>,
    onClick: () -> Unit = { },
) {
    TrPrimaryButton(
        text = {
            if (registerResult is UiResult.Loading) CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            else ButtonText("Sign Up")
        },
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    )
}

@Composable
fun HaveAccountText(
    modifier: Modifier = Modifier,
    goToLogin: () -> Unit = {}
) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
            append("Already have an account? ")
        }
        pushStringAnnotation(
            tag = "Login",
            annotation = "Login"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Login")
        }
        pop()
    }
    ClickableText(
        text = annotatedText,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
    ) { offset ->
        annotatedText.getStringAnnotations(
            tag = "Login",
            start = offset,
            end = offset
        ).firstOrNull()?.let {
            goToLogin()
        }
    }
}
