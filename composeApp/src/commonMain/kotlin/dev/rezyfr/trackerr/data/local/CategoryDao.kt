package dev.rezyfr.trackerr.data.local

import dev.rezyfr.trackerr.domain.model.CategoryType
import migrations.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryDao {
    fun insertCategories(categories: List<CategoryEntity>): Result<Unit>
    fun insertCategory(category: CategoryEntity): Result<Unit>
    fun getCategories(type: CategoryType?) : Flow<List<CategoryEntity>>
}