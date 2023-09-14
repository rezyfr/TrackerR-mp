package dev.rezyfr.trackerr.data.remote.dto.request


import dev.rezyfr.trackerr.domain.model.CategoryType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCategoryRequest(
    @SerialName("color")
    val color: Long,
    @SerialName("iconId")
    val iconId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: String
)