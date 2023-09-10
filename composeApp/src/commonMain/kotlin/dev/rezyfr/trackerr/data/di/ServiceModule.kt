package dev.rezyfr.trackerr.data.di

import dev.rezyfr.trackerr.data.remote.service.AuthService
import dev.rezyfr.trackerr.data.remote.service.AuthServiceImpl
import dev.rezyfr.trackerr.data.remote.service.CategoryService
import dev.rezyfr.trackerr.data.remote.service.CategoryServiceImpl
import dev.rezyfr.trackerr.data.remote.service.IconService
import dev.rezyfr.trackerr.data.remote.service.IconServiceImpl
import dev.rezyfr.trackerr.data.remote.service.TransactionService
import dev.rezyfr.trackerr.data.remote.service.TransactionServiceImpl
import dev.rezyfr.trackerr.data.remote.service.WalletService
import dev.rezyfr.trackerr.data.remote.service.WalletServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun getServiceModule() = module {
    single<AuthService> { AuthServiceImpl(get(), get(named("baseUrl"))) }
    single<IconService> { IconServiceImpl(get(), get(named("baseUrl"))) }
    single<WalletService> { WalletServiceImpl(get(), get(named("baseUrl"))) }
    single<TransactionService> { TransactionServiceImpl(get(), get(named("baseUrl"))) }
    single<CategoryService> { CategoryServiceImpl(get(), get(named("baseUrl"))) }
}