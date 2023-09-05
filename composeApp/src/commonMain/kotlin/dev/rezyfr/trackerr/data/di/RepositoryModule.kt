package dev.rezyfr.trackerr.data.di

import dev.rezyfr.trackerr.data.repository.AuthRepositoryImpl
import dev.rezyfr.trackerr.data.repository.IconRepositoryImpl
import dev.rezyfr.trackerr.data.repository.TransactionRepositoryImpl
import dev.rezyfr.trackerr.data.repository.WalletRepositoryImpl
import dev.rezyfr.trackerr.domain.repository.AuthRepository
import dev.rezyfr.trackerr.domain.repository.IconRepository
import dev.rezyfr.trackerr.domain.repository.TransactionRepository
import dev.rezyfr.trackerr.domain.repository.WalletRepository
import org.koin.dsl.module

fun getRepositoryModule() = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<IconRepository> { IconRepositoryImpl(get(), get(), get()) }
    single<WalletRepository> { WalletRepositoryImpl(get()) }
    single<TransactionRepository> { TransactionRepositoryImpl(get()) }
}