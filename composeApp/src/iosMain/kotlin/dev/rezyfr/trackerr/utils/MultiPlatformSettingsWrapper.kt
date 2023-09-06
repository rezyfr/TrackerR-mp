package dev.rezyfr.trackerr.utils

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import org.koin.core.scope.Scope
import platform.Foundation.NSUserDefaults


actual fun Scope.createSettings(): ObservableSettings {
    return NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
}