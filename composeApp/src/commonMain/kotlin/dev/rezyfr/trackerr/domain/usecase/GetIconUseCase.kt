package dev.rezyfr.trackerr.domain.usecase

import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.UseCase
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.IconModel
import dev.rezyfr.trackerr.domain.model.IconType
import dev.rezyfr.trackerr.domain.repository.IconRepository

class GetIconUseCase(
    private val iconRepository: IconRepository
) : UseCase<UiResult<List<IconModel>>, IconType> {

    override suspend fun execute(params: IconType): UiResult<List<IconModel>> {
        return handleResult(
            block = { iconRepository.getIcons(params) },
            map = {
                it.map { icon ->
                    icon.mapToDomain()
                }
            }
        )
    }
}