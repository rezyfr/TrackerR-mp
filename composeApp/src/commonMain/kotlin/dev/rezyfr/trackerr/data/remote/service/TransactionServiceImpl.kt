package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse
import dev.rezyfr.trackerr.data.util.execute
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url

class TransactionServiceImpl(
    private val httpClient: HttpClient,
    baseUrl: String
) : TransactionService {
    private val recent = "$baseUrl/transaction/recent"
    private val summary = "$baseUrl/transaction/summary"
    override suspend fun getRecentTransaction(): NetworkResponse<BaseDto<List<TransactionResponse>>> {
        return execute {
            httpClient.get {
                url(recent)
            }.body()
        }
    }

    override suspend fun getTransactionSummary(month: Int): NetworkResponse<BaseDto<TransactionSummaryResponse>> {
        return execute {
            httpClient.post {
                url(summary)
                parameter("month", month)
            }.body()
        }
    }
}