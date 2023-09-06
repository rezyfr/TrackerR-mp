package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.request.CreateWalletRequest
import dev.rezyfr.trackerr.data.remote.dto.response.WalletResponse

interface WalletService {
    suspend fun createWallet(request: CreateWalletRequest): NetworkResponse<BaseDto<WalletResponse>>
    suspend fun getWalletBalance(): NetworkResponse<BaseDto<Long>>
}