package dev.rezyfr.trackerr.data.repository

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.request.CreateWalletRequest
import dev.rezyfr.trackerr.data.remote.dto.response.WalletResponse
import dev.rezyfr.trackerr.data.remote.service.WalletService
import dev.rezyfr.trackerr.domain.repository.WalletRepository

class WalletRepositoryImpl(
    private val walletService: WalletService
) : WalletRepository {
    override suspend fun createWallet(request: CreateWalletRequest): NetworkResponse<BaseDto<WalletResponse>> {
        return walletService.createWallet(request)
    }
}