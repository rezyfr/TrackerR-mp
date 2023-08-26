package dev.rezyfr.trackerr.data.di

import dev.rezyfr.trackerr.data.repository.AuthRepositoryImpl
import dev.rezyfr.trackerr.domain.repository.AuthRepository
import org.koin.dsl.module

fun getRepositoryModule() = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}