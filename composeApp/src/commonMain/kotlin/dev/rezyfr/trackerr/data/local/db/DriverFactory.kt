package dev.rezyfr.trackerr.data.local.db

import app.cash.sqldelight.db.SqlDriver
import dev.rezyfr.trackerr.data.local.entity.Database
import org.koin.core.scope.Scope

expect fun Scope.sqlDriverFactory(): SqlDriver

fun createDatabase(driver: SqlDriver): Database {
    return Database(driver)
}