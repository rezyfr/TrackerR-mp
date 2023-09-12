package dev.rezyfr.trackerr.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dev.rezyfr.trackerr.presentation.screens.auth.AuthComponent
import dev.rezyfr.trackerr.presentation.screens.main.MainComponent
import dev.rezyfr.trackerr.presentation.screens.root.RootStore
import dev.rezyfr.trackerr.presentation.screens.root.RootStoreFactory
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class RootComponent(
    componentContext: ComponentContext,
    private val auth: (ComponentContext, StoreFactory) -> AuthComponent,
    private val main: (ComponentContext, StoreFactory) -> MainComponent,
) : KoinComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
    ) : this(
        componentContext = componentContext,
        auth = { context, factory ->
            AuthComponent(
                componentContext = context,
                storeFactory = factory
            )
        },
        main = { context, factory ->
            MainComponent(
                componentContext = context,
                storeFactory = factory
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Auth,
        handleBackButton = true,
        childFactory = ::createChild
    )
    val childStack: Value<ChildStack<*, Child>> get() = stack

    private val store = instanceKeeper.getStore {
        RootStoreFactory(
            storeFactory = get(),
            onTokenValid = { isValid ->
                if (isValid) {
                    navigation.replaceAll(Configuration.Main)
                } else {
                    navigation.replaceAll(Configuration.Auth)
                }
            }
        ).create()
    }

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            is Configuration.Auth -> Child.Auth(
                authComponent = auth(componentContext, get())
            )

            is Configuration.Main -> Child.Main(
                mainComponent = main(componentContext, get())
            )
        }

    fun onEvent(event: RootStore.Intent) {
        store.accept(event)
    }

    fun goToMain() {
        navigation.replaceAll(Configuration.Main)
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Auth : Configuration()

        @Parcelize
        object Main : Configuration()
    }

    sealed class Child {
        data class Main(val mainComponent: MainComponent) : Child()
        data class Auth(val authComponent: AuthComponent) : Child()
    }

    sealed class Action {
        object NavigateToMain: Action()
    }
}