package dev.rezyfr.trackerr.data.service

import dev.rezyfr.trackerr.data.dto.NetworkResponse
import dev.rezyfr.trackerr.data.dto.response.IconResponse
import dev.rezyfr.trackerr.domain.model.IconType

interface IconService {
    suspend fun getIcons(type: IconType): NetworkResponse<List<IconResponse>>
}