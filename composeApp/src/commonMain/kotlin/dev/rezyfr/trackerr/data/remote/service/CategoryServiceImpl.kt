package dev.rezyfr.trackerr.data.remote.service

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.request.CreateCategoryRequest
import dev.rezyfr.trackerr.data.remote.dto.response.CategoryResponse
import dev.rezyfr.trackerr.data.util.SettingsConstant
import dev.rezyfr.trackerr.data.util.execute
import dev.rezyfr.trackerr.data.util.setAuthHeader
import dev.rezyfr.trackerr.data.util.setJsonBody
import dev.rezyfr.trackerr.domain.model.CategoryType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url

class CategoryServiceImpl(
    private val httpClient: HttpClient,
    private val settings: Settings,
    baseUrl: String
) : CategoryService {
    private val category = "$baseUrl/category"
    override suspend fun syncUserCategories(type: String) : NetworkResponse<BaseDto<List<CategoryResponse>>> {
        return execute {
            httpClient.get {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(category)
                parameter("type", type)
            }.body()
        }
    }

    override suspend fun createCategory(
        type: CategoryType,
        name: String,
        color: Long,
        iconId: Int
    ): NetworkResponse<BaseDto<CategoryResponse>> {
        return execute {
            httpClient.post {
                setAuthHeader(settings[SettingsConstant.KEY_ACCESS_TOKEN, ""])
                url(category)
                setJsonBody(
                    CreateCategoryRequest(
                        type = type.name,
                        name = name,
                        color = color,
                        iconId = iconId
                    )
                )
            }.body()
        }
    }
}