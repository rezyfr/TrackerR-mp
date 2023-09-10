package dev.rezyfr.trackerr.domain.model

data class CategoryModel(
    val id: Int,
    val name: String,
    val icon: String,
    val type: CategoryType,
    val color: String
)
