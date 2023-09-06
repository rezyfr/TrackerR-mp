package dev.rezyfr.trackerr.data.mapper

import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.domain.model.TransactionModel

class TransactionMapper {
    fun mapResponseToDomain(response: TransactionResponse) : TransactionModel {
        return TransactionModel(
            id = response.id,
            amount = response.amount.toLong(),
            date = response.createdDate,
            desc = response.description,
            type = response.type,
            categoryIcon = response.categoryIcon,
            category = response.category
        )
    }
}