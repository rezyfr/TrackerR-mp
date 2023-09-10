package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.remote.dto.response.CategoryResponse
import migrations.CategoryEntity

interface CategoryRepository {
    suspend fun syncUserCategories(type: String) : Result<List<CategoryResponse>>
    suspend fun addCategories(categories: List<CategoryEntity>) : Result<Unit>
}