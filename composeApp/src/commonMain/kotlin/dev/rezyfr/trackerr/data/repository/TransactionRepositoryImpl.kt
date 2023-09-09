package dev.rezyfr.trackerr.data.repository

import dev.rezyfr.trackerr.data.remote.dto.handleResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse
import dev.rezyfr.trackerr.data.remote.service.TransactionService
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
}