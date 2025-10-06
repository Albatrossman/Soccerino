package ir.miare.domain.repo

import ir.miare.common.resource.Resource
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.domain.model.id.PlayerId
import ir.miare.domain.model.PlayerSortOption
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    suspend fun getPlayers(
        sortBy: PlayerSortOption,
        offset: Long,
        limit: Int
    ): Resource<List<LeagueWithPlayers>>

    fun getFollowedPlayers(sortBy: PlayerSortOption): Flow<Resource<List<LeagueWithPlayers>>>

    fun getFollowedPlayersIds(): Flow<Resource<List<PlayerId>>>

    suspend fun follow(player: PlayerWithLeague): Resource<Unit>

    suspend fun unfollow(player: PlayerWithLeague): Resource<Unit>

}