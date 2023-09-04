package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.IconResponse
import dev.rezyfr.trackerr.domain.model.IconType

interface IconService {
    suspend fun getIcons(type: IconType): NetworkResponse<BaseDto<List<IconResponse>>>
}