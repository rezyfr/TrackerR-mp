package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionFrequencyResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionWithDateResponse
import dev.rezyfr.trackerr.domain.model.CategoryType
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
    suspend fun getTransactionWithDate(
        sortOrder: String? = null,
        type: CategoryType? = null,
        categoryIds: List<Int>? = null,
    ) : Result<List<TransactionWithDateResponse>>
}