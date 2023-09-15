package dev.rezyfr.trackerr.domain.usecase.transaction

import dev.rezyfr.trackerr.data.mapper.TransactionMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleFlowResult
import dev.rezyfr.trackerr.domain.model.transaction.TransactionModel
import dev.rezyfr.trackerr.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

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
                    createdDate = LocalDateTime(params.selectedYear, params.selectedMonth, params.selectedDay, 0, 0, 1,1).toString(),
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
        val selectedMonth: Int,
        val selectedDay: Int,
        val selectedYear: Int,
        val description: String,
        val walletId: Int
    )
}