package dev.rezyfr.trackerr.utils

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import org.koin.core.scope.Scope
import java.util.prefs.Preferences

actual fun Scope.createSettings(): ObservableSettings {
    return PreferencesSettings(Preferences.userRoot())
}