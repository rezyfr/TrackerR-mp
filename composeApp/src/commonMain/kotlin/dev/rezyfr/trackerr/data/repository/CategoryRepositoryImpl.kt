package dev.rezyfr.trackerr.data.repository

import dev.rezyfr.trackerr.data.local.CategoryDao
import dev.rezyfr.trackerr.data.remote.dto.handleResponse
import dev.rezyfr.trackerr.data.remote.dto.response.CategoryResponse
import dev.rezyfr.trackerr.data.remote.service.CategoryService
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.repository.CategoryRepository
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
}