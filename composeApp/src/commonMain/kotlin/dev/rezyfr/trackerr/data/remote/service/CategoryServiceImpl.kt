package dev.rezyfr.trackerr.data.remote.service

import dev.rezyfr.trackerr.data.remote.dto.BaseDto
import dev.rezyfr.trackerr.data.remote.dto.NetworkResponse
import dev.rezyfr.trackerr.data.remote.dto.response.CategoryResponse
import dev.rezyfr.trackerr.data.util.execute
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class CategoryServiceImpl(
    private val httpClient: HttpClient,
    baseUrl: String
) : CategoryService {
    private val getCategory = "$baseUrl/category"
    override suspend fun syncUserCategories(type: String) : NetworkResponse<BaseDto<List<CategoryResponse>>> {
        return execute {
            httpClient.get {
                url(getCategory)
                parameter("type", type)
            }.body()
        }
    }
}