package dev.rezyfr.trackerr.data.remote.service

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.request.CreateWalletRequest
import dev.rezyfr.trackerr.data.remote.dto.response.WalletResponse
import dev.rezyfr.trackerr.data.util.SettingsConstant
import dev.rezyfr.trackerr.data.util.execute
import dev.rezyfr.trackerr.data.util.setAuthHeader
import dev.rezyfr.trackerr.data.util.setJsonBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url

class WalletServiceImpl(
    private val httpClient: HttpClient,
    private val settings: Settings,
    baseUrl: String
) : WalletService {

    private val getWallet = "$baseUrl/wallet"
    private val create = "$baseUrl/wallet/create"
    private val getBalance = "$baseUrl/wallet/balance"
    override suspend fun createWallet(request: CreateWalletRequest): NetworkResponse<BaseDto<WalletResponse>> {
        return execute {
            httpClient.post {
                url(create)
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                setJsonBody(request)
            }.body()
        }
    }

    override suspend fun getWalletBalance(): NetworkResponse<BaseDto<Long>> {
        return execute {
            httpClient.get {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(getBalance)
            }.body()
        }
    }

    override suspend fun getWallets(): NetworkResponse<BaseDto<List<WalletResponse>>> {
        return execute {
            httpClient.get {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(getWallet)
            }.body()
        }
    }
}