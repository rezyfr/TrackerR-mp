package dev.rezyfr.trackerr.data.repository

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.service.TransactionService
import dev.rezyfr.trackerr.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val transactionService: TransactionService
) : TransactionRepository {
    override suspend fun fetchRecentTransaction(): NetworkResponse<BaseDto<List<TransactionResponse>>> {
        return transactionService.getRecentTransaction()
    }
}