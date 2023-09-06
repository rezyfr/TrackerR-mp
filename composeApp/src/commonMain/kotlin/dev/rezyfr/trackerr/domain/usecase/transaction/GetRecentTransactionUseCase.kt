package dev.rezyfr.trackerr.domain.usecase.transaction

import dev.rezyfr.trackerr.data.mapper.TransactionMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.domain.repository.TransactionRepository

class GetRecentTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val mapper: TransactionMapper
) : UseCase<UiResult<List<TransactionModel>>, Nothing?> {
    override suspend fun execute(params: Nothing?): UiResult<List<TransactionModel>> {
        return handleResult(
            execute = {
                transactionRepository.fetchRecentTransaction()
            },
            onSuccess = { data ->
                data.map { mapper.mapResponseToDomain(it) }
            }
        )
    }
}