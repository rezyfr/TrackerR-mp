package dev.rezyfr.trackerr.domain.usecase.transaction

import dev.rezyfr.trackerr.data.mapper.TransactionMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleFlowResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.transaction.TransactionModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionWithDateModel
import dev.rezyfr.trackerr.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionWithDateUseCase(
    private val transactionRepository: TransactionRepository,
    private val mapper: TransactionMapper
) : UseCase<UiResult<List<TransactionWithDateModel>>, GetTransactionWithDateUseCase.Params?> {
    override  fun executeFlow(params: Params?): Flow<UiResult<List<TransactionWithDateModel>>> {
        return handleFlowResult(
            execute = {
                transactionRepository.getTransactionWithDate(
                    sortOrder = params?.sortOrder,
                    categoryId = params?.categoryId,
                    type = params?.type
                )
            },
            onSuccess = { data ->
                data.map {
                    mapper.mapTrxWithDateResponseToDomain(it)
                }
            }
        )
    }

    data class Params(
        val sortOrder: String? = null,
        val categoryId: Int? = null,
        val type: CategoryType? = null
    )
}