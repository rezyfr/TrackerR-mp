package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionFrequencyResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse
import dev.rezyfr.trackerr.domain.model.Granularity

interface TransactionService {
    suspend fun getRecentTransaction() : NetworkResponse<BaseDto<List<TransactionResponse>>>
    suspend fun getTransactionSummary(month: Int) : NetworkResponse<BaseDto<TransactionSummaryResponse>>
    suspend fun createTransaction(
        amount: Double,
        categoryId: Int,
        createdDate: String,
        description: String,
        walletId: Int
    ) : NetworkResponse<BaseDto<TransactionResponse>>
    suspend fun getTransactionFrequency(
        granularity: Granularity
    ) : NetworkResponse<BaseDto<List<TransactionFrequencyResponse>>>
}