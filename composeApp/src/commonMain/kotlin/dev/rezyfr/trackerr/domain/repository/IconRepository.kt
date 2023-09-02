package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.data.dto.NetworkResponse
import dev.rezyfr.trackerr.data.dto.response.IconResponse
import dev.rezyfr.trackerr.domain.model.IconType

interface IconRepository {
    suspend fun getIcons(type: IconType): NetworkResponse<List<IconResponse>>
}