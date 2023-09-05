package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse

interface TransactionService {
    suspend fun getRecentTransaction() : NetworkResponse<BaseDto<List<TransactionResponse>>>
}