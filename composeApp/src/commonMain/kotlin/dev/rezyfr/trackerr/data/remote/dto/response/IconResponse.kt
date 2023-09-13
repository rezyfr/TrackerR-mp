package dev.rezyfr.trackerr.data.remote.dto.response

import dev.rezyfr.trackerr.domain.model.IconModel
import migrations.IconEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IconResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("url")
    val url: String,
)