package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse

interface TransactionRepository {
    suspend fun fetchRecentTransaction(): NetworkResponse<BaseDto<List<TransactionResponse>>>
}