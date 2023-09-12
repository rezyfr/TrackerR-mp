package dev.rezyfr.trackerr.presentation.screens.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dev.rezyfr.trackerr.presentation.screens.home.HomeComponent
import org.koin.core.component.KoinComponent

class MainComponent(
    componentContext: ComponentContext,
    private val home: (ComponentContext) -> HomeComponent,
) : ComponentContext by componentContext, KoinComponent {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory
    ) : this(
        componentContext = componentContext,
        home = { context ->
            HomeComponent(
                componentContext = context,
                storeFactory = storeFactory
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Main,
        handleBackButton = false,
        childFactory = ::createChild
    )
    val childStack: Value<ChildStack<*, Child>> get() = stack
    val activeChildIndex: Value<Int> = stack.map {
        it.active.instance.index
    }

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): Child =
        when (configuration) {
            is Configuration.Main -> Child.Home(
                homeComponent = home(componentContext)
            )
        }

    fun onTabSelected(index: Int) {
        navigation.bringToFront(
            when (index) {
                0 -> Configuration.Main
                else -> error("Unknown tab index: $index")
            }
        )
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Main : Configuration()
    }

    sealed class Child(val index: Int) {
        data class Home(val homeComponent: HomeComponent) : Child(0)
    }

    sealed class Event {
        data class MessageReceived(val message: String) : Event()
    }
}