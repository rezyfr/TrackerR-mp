package dev.rezyfr.trackerr.data.dto.response

import dev.rezyfr.trackerr.domain.model.IconModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IconResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("url")
    val url: String,
) {
    fun mapToDomain() = IconModel(
        id = id,
        url = url,
    )
}