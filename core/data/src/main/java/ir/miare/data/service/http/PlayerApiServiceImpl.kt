package ir.miare.data.service.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ir.miare.data.model.LeagueWithPlayersDto
import ir.miare.data.model.PlayerSortOptionDto
import javax.inject.Inject

class PlayerApiServiceImpl @Inject constructor(private val client: HttpClient) : PlayerApiService {

    override suspend fun getPlayers(sortBy: PlayerSortOptionDto, offset: Long, limit: Int): List<LeagueWithPlayersDto> {
        return client.get(urlString = "list") {
            this.parameter(key = "sort", value = sortBy.serialName)
            this.parameter(key = "offset", value = offset)
            this.parameter(key = "limit", value = limit)
        }.body()
    }

}