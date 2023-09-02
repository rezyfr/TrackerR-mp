package dev.rezyfr.trackerr.data.di

import dev.rezyfr.trackerr.data.service.AuthService
import dev.rezyfr.trackerr.data.service.AuthServiceImpl
import dev.rezyfr.trackerr.data.service.IconService
import dev.rezyfr.trackerr.data.service.IconServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun getServiceModule() = module {
    single<AuthService> { AuthServiceImpl(get(), get(named("baseUrl"))) }
    single<IconService> { IconServiceImpl(get(), get(named("baseUrl"))) }
}