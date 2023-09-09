package dev.rezyfr.trackerr.domain.usecase.transaction

import dev.rezyfr.trackerr.data.mapper.TransactionMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleFlowResult
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class CreateTransactionUseCase(
    private val repository: TransactionRepository,
    private val mapper: TransactionMapper
) : UseCase<UiResult<TransactionModel>, CreateTransactionUseCase.Param> {

    override fun executeFlow(params: Param?): Flow<UiResult<TransactionModel>> {
        return handleFlowResult(
            execute = {
                if (params == null) throw IllegalArgumentException("Params cannot be null")
                repository.createTransaction(
                    amount = params.amount,
                    categoryId = params.categoryId,
                    createdDate = params.createdDate,
                    description = params.description,
                    walletId = params.walletId
                )
            },
            onSuccess = {
                mapper.mapResponseToDomain(it)
            }
        )
    }

    data class Param(
        val amount: Double,
        val categoryId: Int,
        val createdDate: String,
        val description: String,
        val walletId: Int
    )
}