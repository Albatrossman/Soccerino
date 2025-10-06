package ir.miare.data.service.http

import ir.miare.data.model.LeagueWithPlayersDto
import ir.miare.data.model.PlayerSortOptionDto

interface PlayerApiService {

    suspend fun getPlayers(sortBy: PlayerSortOptionDto, offset: Long, limit: Int): List<LeagueWithPlayersDto>

}