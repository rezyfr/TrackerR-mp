package dev.rezyfr.trackerr.data.repository

import dev.rezyfr.trackerr.data.remote.dto.handleResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionFrequencyResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionReportResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionWithDateResponse
import dev.rezyfr.trackerr.data.remote.service.TransactionService
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.Granularity
import dev.rezyfr.trackerr.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val transactionService: TransactionService
) : TransactionRepository {
    override suspend fun fetchRecentTransaction(): Result<List<TransactionResponse>> {
        return transactionService.getRecentTransaction().handleResponse()
    }

    override suspend fun fetchTransactionSummary(month: Int): Result<TransactionSummaryResponse> {
        return transactionService.getTransactionSummary(month).handleResponse()
    }

    override suspend fun createTransaction(
        amount: Double,
        categoryId: Int,
        createdDate: String,
        description: String,
        walletId: Int
    ): Result<TransactionResponse> {
        return transactionService.createTransaction(
            amount = amount,
            categoryId = categoryId,
            createdDate = createdDate,
            description = description,
            walletId = walletId
        ).handleResponse()
    }

    override suspend fun getTransactionFrequency(granularity: Granularity): Result<List<TransactionFrequencyResponse>> {
        return transactionService.getTransactionFrequency(granularity).handleResponse()
    }

    override suspend fun getTransactionWithDate(
        sortOrder: String?,
        type: CategoryType?,
        categoryIds: String?,
    ): Result<List<TransactionWithDateResponse>> {
        return transactionService.getTransactionWithDate(sortOrder, type, categoryIds).handleResponse()
    }

    override suspend fun getTransactionReport(): Result<TransactionReportResponse> {
        return transactionService.getTransactionReport().handleResponse()
    }
}