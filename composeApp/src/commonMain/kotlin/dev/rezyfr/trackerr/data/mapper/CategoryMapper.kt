package dev.rezyfr.trackerr.data.mapper

import dev.rezyfr.trackerr.data.remote.dto.response.CategoryResponse
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.model.CategoryType
import migrations.CategoryEntity

class CategoryMapper {
    fun mapResponseToDomain(response: CategoryResponse): CategoryModel {
        return CategoryModel(
            id = response.id,
            name = response.name,
            icon = response.icon,
            type = response.type,
            color = response.color
        )
    }

    fun mapDomainToEntity(model: CategoryModel): CategoryEntity {
        return CategoryEntity(
            id = model.id.toLong(),
            name = model.name,
            icon = model.icon,
            type = model.type.name,
            color = model.color
        )
    }

    fun mapResponseToEntity(response: CategoryResponse): CategoryEntity {
        return CategoryEntity(
            id = response.id.toLong(),
            name = response.name,
            icon = response.icon,
            type = response.type.name,
            color = response.color
        )
    }

    fun mapEntityToDomain(entity: CategoryEntity) : CategoryModel {
        return CategoryModel(
            id = entity.id.toInt(),
            name = entity.name,
            icon = entity.icon,
            type = CategoryType.fromString(entity.type),
            color = entity.color
        )
    }
}