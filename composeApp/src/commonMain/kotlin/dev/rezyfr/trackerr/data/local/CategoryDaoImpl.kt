package dev.rezyfr.trackerr.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.rezyfr.trackerr.data.local.entity.Database
import dev.rezyfr.trackerr.data.util.safeTransaction
import dev.rezyfr.trackerr.domain.model.CategoryType
import migrations.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class CategoryDaoImpl(
    private val database: Database
) : CategoryDao {
    override fun insertCategories(categories: List<CategoryEntity>) : Result<Unit> {
        return database.categoryQueries.safeTransaction {
            categories.forEach { category ->
                database.categoryQueries.insertCategory(
                    category.id,
                    category.icon,
                    category.type,
                    category.name,
                    category.color
                )
            }
        }
    }

    override fun insertCategory(category: CategoryEntity): Result<Unit> {
        return database.categoryQueries.safeTransaction {
            database.categoryQueries.insertCategory(
                category.id,
                category.icon,
                category.type,
                category.name,
                category.color
            )
        }
    }

    override fun getCategories(type: CategoryType?): Flow<List<CategoryEntity>> {
        type?.let {
            return database.categoryQueries.getCategories(type.name).asFlow()
                .mapToList(Dispatchers.Default)
        } ?: run {
            return database.categoryQueries.getAllCategories().asFlow()
                .mapToList(Dispatchers.Default)
        }
    }
    override suspend fun getCategoryById(id: Int): CategoryEntity? {
        return database.categoryQueries.getCategoryById(id.toLong()).executeAsOneOrNull()
    }
}