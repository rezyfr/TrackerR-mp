package dev.rezyfr.trackerr.domain.di

import dev.rezyfr.trackerr.domain.usecase.CreateWalletUseCase
import dev.rezyfr.trackerr.domain.usecase.GetIconUseCase
import dev.rezyfr.trackerr.domain.usecase.LoginUseCase
import dev.rezyfr.trackerr.domain.usecase.RegisterUseCase
import org.koin.dsl.module

fun getUseCaseModule() = module {
    // Auth
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }

    // Icon
    single { GetIconUseCase(get()) }

    // Wallet
     single { CreateWalletUseCase(get()) }
}