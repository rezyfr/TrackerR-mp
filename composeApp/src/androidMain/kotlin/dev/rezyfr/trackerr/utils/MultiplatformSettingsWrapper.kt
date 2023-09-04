package dev.rezyfr.trackerr.utils

import android.content.Context
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope

actual fun Scope.createSettings(): ObservableSettings {
    val sharedPreferences =
        androidContext().getSharedPreferences("trackerr", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(delegate = sharedPreferences)
}