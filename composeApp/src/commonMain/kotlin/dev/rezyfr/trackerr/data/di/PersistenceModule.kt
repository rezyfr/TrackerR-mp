package dev.rezyfr.trackerr.data.di

import dev.rezyfr.trackerr.data.local.CategoryDao
import dev.rezyfr.trackerr.data.local.IconDao
import dev.rezyfr.trackerr.data.local.IconDaoImpl
import dev.rezyfr.trackerr.data.local.CategoryDaoImpl
import dev.rezyfr.trackerr.data.local.db.createDatabase
import dev.rezyfr.trackerr.data.local.db.sqlDriverFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun getPersistenceModule() = module {
    factory { sqlDriverFactory() }
    single { createDatabase(get()) }
    singleOf(::IconDaoImpl) bind IconDao::class
    singleOf(::CategoryDaoImpl) bind CategoryDao::class
}