package dev.rezyfr.trackerr.data.local

import dev.rezyfr.trackerr.domain.model.IconType
import devrezyfrtrackerrdata.IconEntity
import kotlinx.coroutines.flow.Flow

interface IconDao {
    fun insertIcon(icon: IconEntity)
    fun getIconByType(type: IconType) : Flow<List<IconEntity>>
}