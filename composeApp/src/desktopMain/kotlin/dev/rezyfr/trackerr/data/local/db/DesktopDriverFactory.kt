package dev.rezyfr.trackerr.data.local.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.rezyfr.trackerr.data.local.entity.Database
import org.koin.core.scope.Scope

actual fun Scope.sqlDriverFactory(): SqlDriver {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    Database.Schema.create(driver)
    return driver
}
