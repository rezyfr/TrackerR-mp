package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.request.CreateWalletRequest
import dev.rezyfr.trackerr.data.remote.dto.response.WalletResponse
import dev.rezyfr.trackerr.data.util.execute
import dev.rezyfr.trackerr.data.util.setJsonBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.url

class WalletServiceImpl(
    private val httpClient: HttpClient,
    baseUrl: String
) : WalletService {

    private val create = "$baseUrl/wallet/create"
    override suspend fun createWallet(request: CreateWalletRequest): NetworkResponse<BaseDto<WalletResponse>> {
        return execute {
            httpClient.post {
                url(create)
                setJsonBody(request)
            }.body()
        }
    }
}