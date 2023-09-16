package dev.rezyfr.trackerr.domain.usecase.transaction

import dev.rezyfr.trackerr.data.mapper.TransactionMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.transaction.TransactionSummaryModel
import dev.rezyfr.trackerr.domain.repository.TransactionRepository

class GetTransactionSummaryUseCase(
    private val transactionRepository: TransactionRepository,
    private val mapper: TransactionMapper
) : UseCase<UiResult<TransactionSummaryModel>, Int> {
    override suspend fun execute(params: Int): UiResult<TransactionSummaryModel> {
        return handleResult(
            execute = {
                 transactionRepository.fetchTransactionSummary(params - 1)
            },
            onSuccess = { data ->
                mapper.mapSummaryResponseToDomain(data)
            }
        )
    }
}