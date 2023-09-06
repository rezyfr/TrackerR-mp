package dev.rezyfr.trackerr.data.local.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.rezyfr.trackerr.data.local.entity.Database
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    return NativeSqliteDriver(Database.Schema, "trackerr.db")
}
