package dev.rezyfr.trackerr.data.mapper

import dev.rezyfr.trackerr.data.remote.dto.response.IconResponse
import dev.rezyfr.trackerr.domain.model.IconModel
import devrezyfrtrackerrdata.IconEntity

class IconMapper {

    fun mapEntityToDomain(entity: IconEntity) = IconModel(
        id = entity.id.toInt(),
        url = entity.url,
    )

    fun mapDomainToEntity(icon: IconModel, type: String) = IconEntity(
        id = icon.id.toLong(),
        url = icon.url,
        type = type,
    )

    fun mapResponseToDomain(response: IconResponse) = IconModel(
        id = response.id,
        url = response.url,
    )

    fun mapResponseToEntity(response: IconResponse, type: String) = IconEntity(
        id = response.id.toLong(),
        url = response.url,
        type = type,
    )
}