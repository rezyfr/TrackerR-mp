package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.response.CategoryResponse
import dev.rezyfr.trackerr.domain.model.CategoryType
import kotlinx.coroutines.flow.Flow
import migrations.CategoryEntity

interface CategoryRepository {
    suspend fun syncUserCategories(type: String) : Result<List<CategoryResponse>>
    suspend fun addCategories(categories: List<CategoryEntity>) : Result<Unit>
    fun addCategory(category: CategoryEntity) : Result<Unit>
    fun getCategories(type: CategoryType?): Flow<List<CategoryEntity>>
    suspend fun createCategory(type: CategoryType, name: String, color: Long, iconId: Int) : Result<CategoryResponse>
}