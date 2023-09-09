package dev.rezyfr.trackerr.data.mapper

import dev.rezyfr.trackerr.data.remote.dto.response.WalletResponse
import dev.rezyfr.trackerr.domain.model.WalletModel

class WalletMapper {
    fun mapResponseToDomain(response: WalletResponse) : WalletModel {
        return WalletModel(
            id = response.id,
            name = response.name,
            balance = response.balance,
            icon = response.icon
        )
    }
}