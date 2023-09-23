package dev.rezyfr.trackerr.data.repository

import dev.rezyfr.trackerr.data.local.CategoryDao
import dev.rezyfr.trackerr.data.remote.dto.handleResponse
import dev.rezyfr.trackerr.data.remote.dto.response.CategoryResponse
import dev.rezyfr.trackerr.data.remote.service.CategoryService
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import migrations.CategoryEntity

class CategoryRepositoryImpl(
    private val categoryService: CategoryService,
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override suspend fun syncUserCategories(type: String): Result<List<CategoryResponse>> {
        return categoryService.syncUserCategories(type).handleResponse()
    }

    override suspend fun addCategories(categories: List<CategoryEntity>): Result<Unit> {
        return categoryDao.insertCategories(categories)
    }

    override fun addCategory(category: CategoryEntity): Result<Unit> {
        return categoryDao.insertCategory(category)
    }

    override fun getCategories(type: CategoryType?): Flow<List<CategoryEntity>> {
        return categoryDao.getCategories(type)
    }

    override suspend fun createCategory(
        type: CategoryType,
        name: String,
        color: Long,
        iconId: Int
    ): Result<CategoryResponse> {
        return categoryService.createCategory(type, name, color, iconId).handleResponse()
    }
    override suspend fun getCategoryById(id: Int): CategoryEntity? {
        return categoryDao.getCategoryById(id)
    }
}