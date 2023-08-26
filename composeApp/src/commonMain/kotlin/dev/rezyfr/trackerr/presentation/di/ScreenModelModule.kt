package dev.rezyfr.trackerr.presentation.di

import dev.rezyfr.trackerr.presentation.screens.login.LoginViewModel
import org.koin.dsl.module


fun getScreenModelModule() = module {
    single { LoginViewModel(get()) }
}