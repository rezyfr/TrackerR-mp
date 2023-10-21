package dev.rezyfr.trackerr.presentation.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.base.ButtonText
import dev.rezyfr.trackerr.presentation.component.base.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.component.base.TrTextField
import dev.rezyfr.trackerr.presentation.component.base.TrTopBar
import dev.rezyfr.trackerr.presentation.screens.login.store.LoginStore

@Composable
fun LoginScreen(
    loginComponent: LoginComponent,
    onLoginSuccess : (() -> Unit),
    ) {
    val loginState by loginComponent.state.collectAsState()

    LoginScreen(
        state = loginState,
        onEvent = loginComponent::onEvent,
        onAction = loginComponent::onAction,
    )

    LaunchedEffect(loginState.loginResult) {
        if (loginState.loginResult is UiResult.Success) {
            onLoginSuccess.invoke()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginStore.State,
    onEvent: (LoginStore.Intent) -> Unit,
    onAction: (LoginComponent.Action) -> Unit,
) {
    Scaffold(
        topBar = {
            TrTopBar(text = "Login", onBackPressed = {
                onAction(LoginComponent.Action.NavigateBack)
            })
        },
    ) {
        LoginContent(
            state = state,
            onEvent = onEvent,
            onAction = onAction,
            modifier = Modifier.padding(it),
        )
    }
}

@Composable
fun LoginContent(
    state: LoginStore.State,
    onEvent: (LoginStore.Intent) -> Unit,
    onAction: (LoginComponent.Action) -> Unit,
    modifier: Modifier
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        VSpacer(36)
        LoginEmailField(
            email = state.email,
            onChangeEmail = { onEvent(LoginStore.Intent.OnEmailChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        VSpacer(24)
        LoginPasswordField(
            password = state.password,
            onChangePassword = { onEvent(LoginStore.Intent.OnPasswordChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        VSpacer(40)
        LoginButton(onLogin = { onEvent(LoginStore.Intent.Login) })
        VSpacer(32)
        NoAccountText(goToRegister = { onAction(LoginComponent.Action.NavigateToRegister) })
        VSpacer(16)
        LoginStateResult(
            state = state,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun LoginStateResult(
    state: LoginStore.State,
    modifier: Modifier = Modifier
) {
    when (state.loginResult) {
        is UiResult.Loading -> {
            CircularProgressIndicator(modifier = modifier)
        }

        is UiResult.Error -> {
            Text(
                text = "${state.loginResult.exception.message}",
                modifier = modifier.padding(16.dp),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error),
                textAlign = TextAlign.Center
            )
        }

        else -> Unit
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
fun LoginPasswordField(
    modifier: Modifier = Modifier,
    onChangePassword: (String) -> Unit = {},
    password: String
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    TrTextField(
        modifier = modifier,
        value = password,
        onValueChange = onChangePassword,
        placeholder = "Password",
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
fun NoAccountText(
    modifier: Modifier = Modifier,
    goToRegister: () -> Unit = {}
) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
            append("Don't have an account yet? ")
        }
        pushStringAnnotation(
            tag = "SignUp",
            annotation = "SignUp"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Register")
        }
        pop()
    }
    ClickableText(
        text = annotatedText,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
    ) { offset ->
        annotatedText.getStringAnnotations(
            tag = "SignUp",
            start = offset,
            end = offset
        ).firstOrNull()?.let {
            goToRegister()
        }
    }
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
        text = { ButtonText(text = "Login") }
    )
}
