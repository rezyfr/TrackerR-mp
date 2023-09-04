package dev.rezyfr.trackerr.utils

import com.russhwolf.settings.ObservableSettings
import org.koin.core.scope.Scope

expect fun Scope.createSettings(): ObservableSettings
