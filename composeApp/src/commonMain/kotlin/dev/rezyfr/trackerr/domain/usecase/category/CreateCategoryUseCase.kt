package dev.rezyfr.trackerr.domain.usecase.category

import dev.rezyfr.trackerr.data.mapper.CategoryMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleFlowResult
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CreateCategoryUseCase(
    private val categoryRepository: CategoryRepository,
    private val mapper: CategoryMapper
) : UseCase<UiResult<Unit>, CreateCategoryUseCase.Params> {

    override fun executeFlow(params: Params?): Flow<UiResult<Unit>> {
        if (params == null) throw IllegalArgumentException("Params cannot be null")
        return handleFlowResult(
            execute = {
                categoryRepository.createCategory(
                    type = params.type,
                    name = params.name,
                    color = params.color,
                    iconId = params.iconId
                )
            }, onSuccess = {
                categoryRepository.addCategory(mapper.mapResponseToEntity(it))
                UiResult.Success(Unit)
            }
        )
    }

    data class Params(
        val type: CategoryType,
        val name: String,
        val color: Long,
        val iconId: Int
    )
}