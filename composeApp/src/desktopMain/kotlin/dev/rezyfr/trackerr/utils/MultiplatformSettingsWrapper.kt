package dev.rezyfr.trackerr.utils

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences

actual class MultiplatformSettingsWrapper {
    actual fun createSettings(): ObservableSettings {
        return PreferencesSettings(Preferences.userRoot().node("trackerr"))
    }
}