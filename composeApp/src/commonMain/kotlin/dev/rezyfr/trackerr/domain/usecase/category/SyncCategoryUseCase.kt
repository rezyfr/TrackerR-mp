package dev.rezyfr.trackerr.domain.usecase.category

import dev.rezyfr.trackerr.data.mapper.CategoryMapper
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.repository.CategoryRepository
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class SyncCategoryUseCase(
    private val categoryRepository: CategoryRepository,
    private val categoryMapper: CategoryMapper
) : UseCase<UiResult<String>, Nothing?> {
    override suspend fun execute(params: Nothing?): UiResult<String> = coroutineScope {
        val categories = listOf(
            async(ioDispatcher) { categoryRepository.syncUserCategories(CategoryType.EXPENSE.name) },
            async(ioDispatcher) { categoryRepository.syncUserCategories(CategoryType.INCOME.name) }
        ).awaitAll()

        val isAllSuccess = categories.all { it.isSuccess }

        if (isAllSuccess) {
            withContext(Dispatchers.IO) {
                categories.flatMap { it.getOrThrow() }.map {
                    categoryMapper.mapResponseToEntity(it)
                }.let { list ->
                    categoryRepository.addCategories(list)
                }
            }
            UiResult.Success("Success")
        } else {
            // get first data that got an error and set that as response
            categories.first { it.isFailure }.let {
                UiResult.Error(Exception(it.exceptionOrNull()))
            }
        }
    }
}
