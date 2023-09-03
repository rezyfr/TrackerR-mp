package dev.rezyfr.trackerr.data.di

import dev.rezyfr.trackerr.data.mapper.IconMapper
import org.koin.dsl.module

fun getMapperModule() = module {
    single { IconMapper() }
}