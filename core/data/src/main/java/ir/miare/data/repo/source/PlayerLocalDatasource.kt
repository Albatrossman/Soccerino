package ir.miare.data.repo.source

import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.domain.model.id.PlayerId
import kotlinx.coroutines.flow.Flow

interface PlayerLocalDatasource {

    fun getFollowedPlayers(): Flow<List<LeagueWithPlayers>>

    fun getFollowedPlayersIds(): Flow<List<PlayerId>>

    suspend fun follow(player: PlayerWithLeague)

    suspend fun unfollow(player: PlayerWithLeague)

}