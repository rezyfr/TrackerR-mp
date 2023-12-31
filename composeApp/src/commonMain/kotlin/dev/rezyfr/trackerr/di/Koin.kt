package dev.rezyfr.trackerr.di

import dev.rezyfr.trackerr.data.di.getMapperModule
import dev.rezyfr.trackerr.data.di.getNetworkModule
import dev.rezyfr.trackerr.data.di.getPersistenceModule
import dev.rezyfr.trackerr.data.di.getRepositoryModule
import dev.rezyfr.trackerr.data.di.getServiceModule
import dev.rezyfr.trackerr.domain.di.getUseCaseModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    enableNetworkLogs: Boolean = false,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(listOf(
        getNetworkModule(enableNetworkLogs),
        getRepositoryModule(),
        getServiceModule(),
        getUseCaseModule(),
        getMapperModule(),
        getPersistenceModule()
    ))
}
