package dev.rezyfr.trackerr.data.repository

import dev.rezyfr.trackerr.data.local.IconDao
import dev.rezyfr.trackerr.data.mapper.IconMapper
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.service.IconService
import dev.rezyfr.trackerr.domain.model.IconModel
import dev.rezyfr.trackerr.domain.model.IconType
import dev.rezyfr.trackerr.domain.repository.IconRepository
import dev.rezyfr.trackerr.ioDispatcher
import io.github.aakira.napier.Napier
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class IconRepositoryImpl(
    private val iconService: IconService,
    private val iconDao: IconDao,
    private val iconMapper: IconMapper
) : IconRepository {
    override suspend fun fetchIcon(type: IconType) {
        when (val response = iconService.getIcons(type)) {
            is NetworkResponse.Success -> {
                response.data.data?.forEach {
                    iconDao.insertIcon(iconMapper.mapResponseToEntity(it, type.name))
                }
            }

            is NetworkResponse.Failure -> {
                Napier.e("Error: ${response.throwable.message}")
            }
        }
    }

    override fun getIcons(type: IconType): Flow<List<IconModel>> {
        val cacheIcon = iconDao.getIconByType(type)

        return cacheIcon.map {
            it.map { entity ->
                iconMapper.mapEntityToDomain(entity)
            }
        }
    }
}