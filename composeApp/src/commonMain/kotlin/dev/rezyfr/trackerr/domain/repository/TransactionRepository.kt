package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse

interface TransactionRepository {
    suspend fun fetchRecentTransaction(): Result<List<TransactionResponse>>
    suspend fun fetchTransactionSummary(month: Int): Result<TransactionSummaryResponse>
    suspend fun createTransaction(
        amount: Double,
        categoryId: Int,
        createdDate: String,
        description: String,
        walletId: Int
    ): Result<TransactionResponse>
}