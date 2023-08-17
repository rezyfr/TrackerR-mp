package dev.rezyfr.trackerr.di

import dev.rezyfr.trackerr.screens.auth.AuthViewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    enableNetworkLogs: Boolean = false,
    baseUrl: String,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(getScreenModelModule())
}

fun getScreenModelModule() = module {
    single { AuthViewModel() }
}