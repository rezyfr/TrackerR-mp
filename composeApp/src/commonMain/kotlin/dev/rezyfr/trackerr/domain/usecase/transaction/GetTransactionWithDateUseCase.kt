package dev.rezyfr.trackerr.domain.usecase.transaction

import dev.rezyfr.trackerr.data.mapper.TransactionMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleFlowResult
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.transaction.TransactionWithDateModel
import dev.rezyfr.trackerr.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionWithDateUseCase(
    private val transactionRepository: TransactionRepository,
    private val mapper: TransactionMapper
) : UseCase<UiResult<List<TransactionWithDateModel>>, GetTransactionWithDateUseCase.Params?> {
    override fun executeFlow(params: Params?): Flow<UiResult<List<TransactionWithDateModel>>> {
        return handleFlowResult(
            execute = {
                val sortOrder = if (params?.sortOrder == "Oldest") "ASC"
                else "DESC"
                val categoryIds = if (params?.categoryIds?.toList().isNullOrEmpty()) null else
                    params?.categoryIds?.joinToString(separator = ",")
                transactionRepository.getTransactionWithDate(
                    sortOrder = sortOrder,
                    categoryIds = categoryIds,
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
        val categoryIds: Sequence<Int>? = null,
        val type: CategoryType? = null
    )
}