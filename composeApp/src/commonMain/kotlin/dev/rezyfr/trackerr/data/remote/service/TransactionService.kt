package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse

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
}