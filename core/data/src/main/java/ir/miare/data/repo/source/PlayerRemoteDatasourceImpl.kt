package ir.miare.data.repo.source

import ir.miare.data.mapper.domain
import ir.miare.data.mapper.dto
import ir.miare.data.service.http.PlayerApiService
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.PlayerSortOption
import javax.inject.Inject

class PlayerRemoteDatasourceImpl @Inject constructor(
    private val source: PlayerApiService
) : PlayerRemoteDatasource {

    override suspend fun getPlayers(sortBy: PlayerSortOption, offset: Long, limit: Int): List<LeagueWithPlayers> {
        return source.getPlayers(
            sortBy = sortBy.dto(),
            offset = offset,
            limit = limit
        ).map { it.domain() }
    }

}