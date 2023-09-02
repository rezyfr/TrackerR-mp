package dev.rezyfr.trackerr.data.repository

import dev.rezyfr.trackerr.data.dto.NetworkResponse
import dev.rezyfr.trackerr.data.dto.handleResponse
import dev.rezyfr.trackerr.data.dto.response.IconResponse
import dev.rezyfr.trackerr.data.service.IconService
import dev.rezyfr.trackerr.domain.model.IconModel
import dev.rezyfr.trackerr.domain.model.IconType
import dev.rezyfr.trackerr.domain.repository.IconRepository

class IconRepositoryImpl(
    private val iconService: IconService
) : IconRepository {
    override suspend fun getIcons(type: IconType): NetworkResponse<List<IconResponse>> {
        return handleResponse { iconService.getIcons(type) }
    }
}