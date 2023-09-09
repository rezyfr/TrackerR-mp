package dev.rezyfr.trackerr.presentation.base

import cafe.adriel.voyager.core.model.ScreenModel
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseScreenModel : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContext: CoroutineContext = job + ioDispatcher
    val viewModelScope = CoroutineScope(coroutineContext)

    override fun onDispose() {
        job.complete()
    }
}