package dev.rezyfr.trackerr.presentation.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dev.rezyfr.trackerr.presentation.screens.RootViewModel
import dev.rezyfr.trackerr.presentation.screens.create.account.AddAccountViewModel
import dev.rezyfr.trackerr.presentation.screens.create.transaction.AddTransactionViewModel
import dev.rezyfr.trackerr.presentation.screens.home.HomeViewModel
import dev.rezyfr.trackerr.presentation.screens.login.LoginComponent
import dev.rezyfr.trackerr.presentation.screens.login.LoginViewModel
import dev.rezyfr.trackerr.presentation.screens.login.store.LoginStoreFactory
import dev.rezyfr.trackerr.presentation.screens.register.RegisterComponent
import dev.rezyfr.trackerr.presentation.screens.register.RegisterViewModel
import org.koin.dsl.module


fun getScreenModelModule() = module {
    single { LoginViewModel(get(), get()) }
    single { RegisterViewModel(get()) }
    single { AddAccountViewModel(get(), get()) }

    single { RootViewModel(get()) }
    single { HomeViewModel(get(), get(), get()) }
    single { AddTransactionViewModel(get(), get(), get()) }

    single<StoreFactory> { DefaultStoreFactory() }

    factory { (componentContext: ComponentContext, action: (LoginComponent.Action) -> Unit) ->
        LoginComponent(
            componentContext = componentContext,
            storeFactory = get(),
            action = action
        )
    }

    factory { (componentContext: ComponentContext, action: (RegisterComponent.Action) -> Unit) ->
        RegisterComponent(
            componentContext = componentContext,
            storeFactory = get(),
            action = action
        )
    }
}