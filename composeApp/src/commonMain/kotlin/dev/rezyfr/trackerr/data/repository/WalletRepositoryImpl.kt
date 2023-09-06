package dev.rezyfr.trackerr.data.repository

import dev.rezyfr.trackerr.data.remote.dto.handleResponse
import dev.rezyfr.trackerr.data.remote.dto.request.CreateWalletRequest
import dev.rezyfr.trackerr.data.remote.dto.response.WalletResponse
import dev.rezyfr.trackerr.data.remote.service.WalletService
import dev.rezyfr.trackerr.domain.repository.WalletRepository

class WalletRepositoryImpl(
    private val walletService: WalletService
) : WalletRepository {
    override suspend fun createWallet(request: CreateWalletRequest): Result<WalletResponse> {
        return walletService.createWallet(request).handleResponse()
    }

    override suspend fun getWalletBalance(): Result<Long> {
        return walletService.getWalletBalance().handleResponse()
    }
}