package dev.rezyfr.trackerr

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

//actual fun platformModule() = module { single { Android.create() } }

actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO