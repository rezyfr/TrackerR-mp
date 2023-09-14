package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.CategoryResponse
import dev.rezyfr.trackerr.domain.model.CategoryType

interface CategoryService {
    suspend fun syncUserCategories(type: String) : NetworkResponse<BaseDto<List<CategoryResponse>>>
    suspend fun createCategory(type: CategoryType, name: String, color: Long, iconId: Int) : NetworkResponse<BaseDto<CategoryResponse>>
}