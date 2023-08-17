package dev.rezyfr.trackerr

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

//actual fun platformModule() = module { single { Java.create() } }

actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO