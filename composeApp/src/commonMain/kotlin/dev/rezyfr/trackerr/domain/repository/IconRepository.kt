package dev.rezyfr.trackerr.domain.repository

import dev.rezyfr.trackerr.domain.model.IconModel
import dev.rezyfr.trackerr.domain.model.IconType
import kotlinx.coroutines.flow.Flow

interface IconRepository {
    suspend fun fetchIcon(type: IconType)
    fun getIcons(type: IconType): Flow<List<IconModel>>
}