package ir.miare.data.repo.source

import ir.miare.common.util.DispatcherProvider
import ir.miare.data.mapper.domain
import ir.miare.data.mapper.entity
import ir.miare.data.service.room.dao.FollowedPlayerDao
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.domain.model.id.PlayerId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerLocalDatasourceImpl @Inject constructor(
    private val dispatches: DispatcherProvider,
    private val source: FollowedPlayerDao
) : PlayerLocalDatasource {

    override fun getFollowedPlayers(): Flow<List<LeagueWithPlayers>> {
        return source.getAll().map { entities -> entities.domain() }
    }

    override fun getFollowedPlayersIds(): Flow<List<PlayerId>> {
        return source.getIds()
            .map { it.map { id -> PlayerId(value = id) } }
            .flowOn(context = dispatches.io)
    }

    override suspend fun follow(player: PlayerWithLeague) {
        return source.insert(obj = player.entity())
    }

    override suspend fun unfollow(player: PlayerWithLeague) {
        return source.delete(obj = player.entity())
    }

}