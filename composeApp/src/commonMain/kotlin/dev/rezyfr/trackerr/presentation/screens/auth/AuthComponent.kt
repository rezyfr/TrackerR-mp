package dev.rezyfr.trackerr.presentation.screens.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dev.rezyfr.trackerr.presentation.screens.login.LoginComponent
import dev.rezyfr.trackerr.presentation.screens.register.RegisterComponent
import org.koin.core.component.KoinComponent

class AuthComponent(
    componentContext: ComponentContext,
    private val register: (ComponentContext, (RegisterComponent.Action) -> Unit) -> RegisterComponent,
    private val login: (ComponentContext, (LoginComponent.Action) -> Unit) -> LoginComponent
): ComponentContext by componentContext, KoinComponent {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory
    ) : this  (
        componentContext = componentContext,
        register = { context, action ->
            RegisterComponent(
                componentContext = context,
                storeFactory = storeFactory,
                action = action
            )
        },
        login = { context, action ->
            LoginComponent(
                componentContext = context,
                storeFactory = storeFactory,
                action = action
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Login,
        handleBackButton = true,
        childFactory = ::createChild
    )
    val childStack: Value<ChildStack<*, Child>> get() = stack

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when (configuration) {
            is Configuration.Login -> Child.Login(
                loginComponent = login(componentContext, ::onLoginAction)
            )
            is Configuration.Register -> Child.Register(
                registerComponent = register(componentContext, ::onRegisterAction)
            )
        }

    private fun onLoginAction(action: LoginComponent.Action) {
        when (action) {
            is LoginComponent.Action.NavigateToRegister -> navigation.push(Configuration.Register)
            is LoginComponent.Action.NavigateBack -> navigation.pop()
        }
    }

    private fun onRegisterAction(action: RegisterComponent.Action) {
        when (action) {
            is RegisterComponent.Action.NavigateToLogin -> navigation.pop()
            is RegisterComponent.Action.NavigateBack -> navigation.pop()
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Login : Configuration()
        @Parcelize
        object Register : Configuration()
    }

    sealed class Child {
        data class Login(val loginComponent: LoginComponent) : Child()
        data class Register(val registerComponent: RegisterComponent) : Child()
    }

    sealed class Action {
        object NavigateToMain: Action()
    }
}