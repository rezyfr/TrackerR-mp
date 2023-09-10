package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.CategoryResponse

interface CategoryService {
    suspend fun syncUserCategories(type: String) : NetworkResponse<BaseDto<List<CategoryResponse>>>
}