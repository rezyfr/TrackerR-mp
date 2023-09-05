package dev.rezyfr.trackerr.presentation.di

import dev.rezyfr.trackerr.presentation.screens.create.account.AddAccountViewModel
import dev.rezyfr.trackerr.presentation.screens.home.HomeViewModel
import dev.rezyfr.trackerr.presentation.screens.login.LoginViewModel
import dev.rezyfr.trackerr.presentation.screens.register.RegisterViewModel
import org.koin.dsl.module


fun getScreenModelModule() = module {
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { AddAccountViewModel(get(), get()) }

    single { HomeViewModel(get())}
}