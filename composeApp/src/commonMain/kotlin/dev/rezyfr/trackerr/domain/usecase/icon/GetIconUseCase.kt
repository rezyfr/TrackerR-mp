package dev.rezyfr.trackerr.domain.usecase.icon

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.model.IconModel
import dev.rezyfr.trackerr.domain.model.IconType
import dev.rezyfr.trackerr.domain.repository.IconRepository
import dev.rezyfr.trackerr.ioDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class GetIconUseCase(
    private val iconRepository: IconRepository
) : UseCase<UiResult<List<IconModel>>, IconType> {
    override fun executeFlow(params: IconType?): Flow<UiResult<List<IconModel>>> {
        return iconRepository.getIcons(params!!)
            .onStart {
                withContext(ioDispatcher) {
                    iconRepository.fetchIcon(params)
                }
            }
            .map {
                UiResult.Success(it)
            }
    }
}