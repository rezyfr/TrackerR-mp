package dev.rezyfr.trackerr.data.remote.dto.response


import dev.rezyfr.trackerr.domain.model.CategoryType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("color")
    val color: String,
    @SerialName("icon")
    val icon: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: CategoryType
)