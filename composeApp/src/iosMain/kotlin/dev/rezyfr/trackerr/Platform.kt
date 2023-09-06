package dev.rezyfr.trackerr

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


actual val ioDispatcher: CoroutineDispatcher
    get() = Dispatchers.IO