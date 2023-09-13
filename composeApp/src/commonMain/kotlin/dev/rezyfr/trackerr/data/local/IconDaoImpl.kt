package dev.rezyfr.trackerr.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.rezyfr.trackerr.data.local.entity.Database
import dev.rezyfr.trackerr.domain.model.IconType
import migrations.IconEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class IconDaoImpl(
    private val database: Database
) : IconDao {
    override fun insertIcon(icon: IconEntity) {
        return database.iconQueries.insertIcon(icon.id, icon.url, icon.type)
    }

    override fun getIconByType(type: IconType) : Flow<List<IconEntity>> {
        return database.iconQueries.getIconByType(type.name).asFlow().mapToList(Dispatchers.Default)
    }
}