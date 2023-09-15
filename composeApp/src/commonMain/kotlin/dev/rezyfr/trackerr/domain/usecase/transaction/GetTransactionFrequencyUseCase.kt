package dev.rezyfr.trackerr.domain.usecase.transaction

import dev.rezyfr.trackerr.data.mapper.TransactionMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.Granularity
import dev.rezyfr.trackerr.domain.model.transaction.TransactionFrequencyModel
import dev.rezyfr.trackerr.domain.repository.TransactionRepository

class GetTransactionFrequencyUseCase(
    private val transactionRepository: TransactionRepository,
    private val mapper: TransactionMapper
) : UseCase<UiResult<TransactionFrequencyModel>, Granularity> {
    override suspend fun execute(params: Granularity): UiResult<TransactionFrequencyModel> {
        return handleResult(
            execute = {
                 transactionRepository.getTransactionFrequency(params)
            },
            onSuccess = { data ->
                mapper.mapFrequencyResponseToDomain(data, params)
            }
        )
    }
}