package ir.miare.data.repo.source

import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.PlayerSortOption

interface PlayerRemoteDatasource {

    suspend fun getPlayers(sortBy: PlayerSortOption, offset: Long, limit: Int): List<LeagueWithPlayers>

}