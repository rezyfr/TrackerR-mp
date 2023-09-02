package dev.rezyfr.trackerr.data.service

import dev.rezyfr.trackerr.data.dto.NetworkResponse
import dev.rezyfr.trackerr.data.dto.response.IconResponse
import dev.rezyfr.trackerr.data.util.execute
import dev.rezyfr.trackerr.domain.model.IconType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class IconServiceImpl(
    private val httpClient: HttpClient,
    baseUrl: String
) : IconService {

    private val getIcons = "$baseUrl/icon"
    override suspend fun getIcons(type: IconType): NetworkResponse<List<IconResponse>> {
        return execute {
            httpClient.get {
                url(getIcons)
                parameter("type", type.name)
            }.body()
        }
    }
}