package dev.rezyfr.trackerr.domain.usecase.category

import dev.rezyfr.trackerr.data.mapper.CategoryMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository,
    private val mapper: CategoryMapper
) : UseCase<UiResult<List<CategoryModel>>, CategoryType> {
    override fun executeFlow(params: CategoryType?): Flow<UiResult<List<CategoryModel>>> {
        return categoryRepository.getCategories(params!!).catch {
            UiResult.Error(Exception(it))
        }.map {
            UiResult.Success(it.map(mapper::mapEntityToDomain))
        }
    }
}