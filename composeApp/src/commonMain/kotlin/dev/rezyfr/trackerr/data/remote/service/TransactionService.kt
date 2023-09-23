package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionFrequencyResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionReportResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionWithDateResponse
import dev.rezyfr.trackerr.domain.model.CategoryType
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
    suspend fun getTransactionWithDate(
        sortOrder: String? = null,
        type: CategoryType? = null,
        categoryIds: String? = null,
    ) : NetworkResponse<BaseDto<List<TransactionWithDateResponse>>>
    suspend fun getTransactionReport() : NetworkResponse<BaseDto<TransactionReportResponse>>
}