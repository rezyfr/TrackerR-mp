package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.request.CreateWalletRequest
import dev.rezyfr.trackerr.data.remote.dto.response.WalletResponse

interface WalletRepository {
    suspend fun createWallet(request: CreateWalletRequest) : Result<WalletResponse>
    suspend fun getWalletBalance() : Result<Long>
}