package dev.rezyfr.trackerr.data.remote.service

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.request.CreateTransactionRequest
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionFrequencyResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionReportResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionWithDateResponse
import dev.rezyfr.trackerr.data.util.SettingsConstant
import dev.rezyfr.trackerr.data.util.execute
import dev.rezyfr.trackerr.data.util.setAuthHeader
import dev.rezyfr.trackerr.data.util.setJsonBody
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.Granularity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url

class TransactionServiceImpl(
    private val httpClient: HttpClient,
    private val settings: Settings,
    baseUrl: String
) : TransactionService {
    private val recent = "$baseUrl/transaction/recent"
    private val summary = "$baseUrl/transaction/summary"
    private val create = "$baseUrl/transaction"
    private val frequency = "$baseUrl/transaction/frequency"
    private val withDate = "$baseUrl/transaction/with-date"
    private val report = "$baseUrl/transaction/report"
    override suspend fun getRecentTransaction(): NetworkResponse<BaseDto<List<TransactionResponse>>> {
        return execute {
            httpClient.get {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(recent)
            }.body()
        }
    }

    override suspend fun getTransactionSummary(month: Int): NetworkResponse<BaseDto<TransactionSummaryResponse>> {
        return execute {
            httpClient.post {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(summary)
                parameter("month", month)
            }.body()
        }
    }

    override suspend fun createTransaction(
        amount: Double,
        categoryId: Int,
        createdDate: String,
        description: String,
        walletId: Int
    ): NetworkResponse<BaseDto<TransactionResponse>> {
        return execute {
            httpClient.post {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(create)
                setJsonBody(
                    CreateTransactionRequest(
                        amount = amount,
                        categoryId = categoryId,
                        createdDate = createdDate,
                        description = description,
                        walletId = walletId
                    )
                )
            }.body()
        }
    }

    override suspend fun getTransactionFrequency(granularity: Granularity): NetworkResponse<BaseDto<List<TransactionFrequencyResponse>>> {
        return execute {
            httpClient.get {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(frequency)
                parameter("granularity", granularity.name)
            }.body()
        }
    }

    override suspend fun getTransactionWithDate(
        sortOrder: String?,
        type: CategoryType?,
        categoryIds: String?
    ): NetworkResponse<BaseDto<List<TransactionWithDateResponse>>> {
        return execute {
            httpClient.get {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(withDate)
                type?.let { parameter("type", it.name) }
                sortOrder?.let { parameter("sortOrder", it) }
                categoryIds?.let { parameter("categoryId", categoryIds) }
            }.body()
        }
    }

    override suspend fun getTransactionReport(): NetworkResponse<BaseDto<TransactionReportResponse>> {
        return execute {
            httpClient.get {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(report)
            }.body()
        }
    }
}