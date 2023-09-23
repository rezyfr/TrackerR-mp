package dev.rezyfr.trackerr.domain.usecase.transaction

import dev.rezyfr.trackerr.data.mapper.CategoryMapper
import dev.rezyfr.trackerr.data.mapper.TransactionMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleFlowResult
import dev.rezyfr.trackerr.domain.model.transaction.TransactionReportModel
import dev.rezyfr.trackerr.domain.repository.CategoryRepository
import dev.rezyfr.trackerr.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionReportUseCase(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val categoryMapper: CategoryMapper,
    private val mapper: TransactionMapper
) : UseCase<UiResult<TransactionReportModel>, Nothing> {
    override fun executeFlow(params: Nothing?): Flow<UiResult<TransactionReportModel>> {
        return handleFlowResult(
            execute = {
                transactionRepository.getTransactionReport()
            },
            onSuccess = { data ->
                mapper.mapReportResponseToDomain(data) {
                    if (it == null) return@mapReportResponseToDomain null
                    val cat = categoryRepository.getCategoryById(it) ?: return@mapReportResponseToDomain null
                    categoryMapper.mapEntityToDomain(cat)
                }
            }
        )
    }
}