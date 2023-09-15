package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.response.TransactionFrequencyResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse
import dev.rezyfr.trackerr.domain.model.Granularity

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
    suspend fun getTransactionFrequency(
        granularity: Granularity
    ) : Result<List<TransactionFrequencyResponse>>
}