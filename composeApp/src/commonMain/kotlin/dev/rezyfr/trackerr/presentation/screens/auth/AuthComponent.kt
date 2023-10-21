package dev.rezyfr.trackerr.presentation.screens.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dev.rezyfr.trackerr.presentation.screens.create.account.AddAccountComponent
import dev.rezyfr.trackerr.presentation.screens.login.LoginComponent
import dev.rezyfr.trackerr.presentation.screens.onboarding.OnboardingComponent
import dev.rezyfr.trackerr.presentation.screens.register.RegisterComponent
import dev.rezyfr.trackerr.presentation.screens.start.StartComponent
import org.koin.core.component.KoinComponent

class AuthComponent(
    componentContext: ComponentContext,
    private val register: (ComponentContext, (RegisterComponent.Action) -> Unit) -> RegisterComponent,
    private val login: (ComponentContext, (LoginComponent.Action) -> Unit) -> LoginComponent,
    private val onboarding: (ComponentContext, (OnboardingComponent.Action) -> Unit) -> OnboardingComponent,
    private val start: (ComponentContext, (StartComponent.Action) -> Unit) -> StartComponent,
    private val addAccount: (ComponentContext, (AddAccountComponent.Action) -> Unit) -> AddAccountComponent,
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
        },
        onboarding = { context, action ->
            OnboardingComponent(
                componentContext = context,
                action = action
            )
        },
        start = { context, action ->
            StartComponent(
                componentContext = context,
                action = action
            )
        },
        addAccount = { context, action ->
            AddAccountComponent(
                componentContext = context,
                storeFactory = storeFactory,
                action = action
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Start,
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

            is Configuration.OnBoarding -> Child.Onboarding(
                onboardingComponent = onboarding(componentContext, ::onOnboardingAction)
            )

            is Configuration.Start -> Child.Start(
                startComponent = start(componentContext, ::onStartAction)
            )

            Configuration.OnBoardingAddAccount -> Child.OnBoardingAddAccount(
                onboardingAddAccountComponent = addAccount(componentContext, ::onAddAccountAction)
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
            is RegisterComponent.Action.NavigateToOnboarding -> navigation.replaceCurrent(Configuration.OnBoarding)
        }
    }

    private fun onOnboardingAction(action: OnboardingComponent.Action) {
        when (action) {
            is OnboardingComponent.Action.NavigateToCreateAccount -> navigation.replaceCurrent(Configuration.OnBoardingAddAccount)
        }
    }

    private fun onStartAction(action: StartComponent.Action) {
        when (action) {
            is StartComponent.Action.NavigateToLogin -> navigation.replaceCurrent(Configuration.Login)
            is StartComponent.Action.NavigateToRegister -> navigation.replaceCurrent(Configuration.Register)
        }
    }

    private fun onAddAccountAction(action: AddAccountComponent.Action) {
        when (action) {
            is AddAccountComponent.Action.NavigateBack -> navigation.pop()
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Start : Configuration()
        @Parcelize
        object Login : Configuration()
        @Parcelize
        object Register : Configuration()
        @Parcelize
        object OnBoarding : Configuration()
        @Parcelize
        object OnBoardingAddAccount : Configuration()
    }

    sealed class Child {
        data class Start(val startComponent: StartComponent) : Child()
        data class Login(val loginComponent: LoginComponent) : Child()
        data class Register(val registerComponent: RegisterComponent) : Child()
        data class Onboarding(val onboardingComponent: OnboardingComponent) : Child()
        data class OnBoardingAddAccount(val onboardingAddAccountComponent: AddAccountComponent) : Child()
    }
}