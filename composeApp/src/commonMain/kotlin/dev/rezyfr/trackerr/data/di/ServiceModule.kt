package dev.rezyfr.trackerr.data.di

import dev.rezyfr.trackerr.data.remote.service.AuthService
import dev.rezyfr.trackerr.data.remote.service.AuthServiceImpl
import dev.rezyfr.trackerr.data.remote.service.IconService
import dev.rezyfr.trackerr.data.remote.service.IconServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun getServiceModule() = module {
    single<AuthService> { AuthServiceImpl(get(), get(named("baseUrl"))) }
    single<IconService> { IconServiceImpl(get(), get(named("baseUrl"))) }
}