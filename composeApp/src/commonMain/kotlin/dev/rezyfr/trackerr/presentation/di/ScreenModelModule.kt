package dev.rezyfr.trackerr.presentation.di

import dev.rezyfr.trackerr.presentation.screens.RootViewModel
import dev.rezyfr.trackerr.presentation.screens.create.account.AddAccountViewModel
import dev.rezyfr.trackerr.presentation.screens.create.transaction.AddTransactionViewModel
import dev.rezyfr.trackerr.presentation.screens.home.HomeViewModel
import dev.rezyfr.trackerr.presentation.screens.login.LoginViewModel
import dev.rezyfr.trackerr.presentation.screens.register.RegisterViewModel
import org.koin.dsl.module


fun getScreenModelModule() = module {
    single { LoginViewModel(get(), get()) }
    single { RegisterViewModel(get()) }
    single { AddAccountViewModel(get(), get()) }

    single { RootViewModel(get()) }
    single { HomeViewModel(get(), get(), get())}
    single { AddTransactionViewModel(get(), get(), get()) }
}