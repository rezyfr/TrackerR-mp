package dev.rezyfr.trackerr.data.util

import app.cash.sqldelight.Transacter

fun <T> Transacter.safeTransaction(
    block: () -> T
) : Result<T> {
    return try {
        transaction {
            afterCommit { block() }
        }
        Result.success(block())
    } catch (e: Exception) {
        Result.failure(e)
    }
}