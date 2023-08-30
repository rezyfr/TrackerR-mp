package dev.rezyfr.trackerr.utils

import android.content.Context
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings

actual class MultiplatformSettingsWrapper(private val context: Context) {
    actual fun createSettings(): ObservableSettings {
        val sharedPreferences =
            context.getSharedPreferences("trackerr", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate = sharedPreferences)
    }
}