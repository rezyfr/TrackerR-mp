package dev.rezyfr.trackerr.presentation.screens.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.rezyfr.trackerr.presentation.screens.auth.AuthComponent
import dev.rezyfr.trackerr.presentation.screens.create.transaction.AddTransactionComponent
import dev.rezyfr.trackerr.presentation.screens.main.MainComponent
import dev.rezyfr.trackerr.presentation.screens.root.store.RootStore
import dev.rezyfr.trackerr.presentation.screens.root.store.RootStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class RootComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val auth: (ComponentContext) -> AuthComponent,
    private val main: (ComponentContext) -> MainComponent,
) : KoinComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory
    ) : this(
        componentContext = componentContext,
        storeFactory = storeFactory,
        auth = { context ->
            AuthComponent(
                componentContext = context,
                storeFactory = storeFactory
            )
        },
        main = { context ->
            MainComponent(
                componentContext = context,
                storeFactory = storeFactory,
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
            storeFactory = storeFactory,
            onTokenValid = { isValid ->
                if (isValid) {
                    navigation.replaceAll(Configuration.Main)
                }
            }
        ).create()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    val state = store.stateFlow

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            is Configuration.Auth -> Child.Auth(
                authComponent = auth(componentContext)
            )

            is Configuration.Main -> Child.Main(
                mainComponent = main(componentContext)
            )
        }

    fun onAction(action: Action) {
        when (action) {
            is Action.NavigateToMain -> navigation.replaceAll(Configuration.Main)
        }
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
        object NavigateToMain : Action()
    }
}