package dev.rezyfr.trackerr.domain.di

import dev.rezyfr.trackerr.domain.usecase.LoginUseCase
import org.koin.dsl.module

fun getUseCaseModule() = module {
    single { LoginUseCase(get()) }
}