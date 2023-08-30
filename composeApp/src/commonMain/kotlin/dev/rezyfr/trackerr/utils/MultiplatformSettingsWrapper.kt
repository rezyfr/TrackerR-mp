package dev.rezyfr.trackerr.utils

import com.russhwolf.settings.ObservableSettings

expect class MultiplatformSettingsWrapper {
    fun createSettings(): ObservableSettings
}