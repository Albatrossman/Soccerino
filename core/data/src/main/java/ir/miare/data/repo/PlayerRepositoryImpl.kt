package ir.miare.data.repo

import ir.miare.common.resource.Resource
import ir.miare.data.repo.source.PlayerLocalDatasource
import ir.miare.data.repo.source.PlayerRemoteDatasource
import ir.miare.data.util.asResourceFlow
import ir.miare.data.util.catching
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.domain.model.id.PlayerId
import ir.miare.domain.repo.PlayerRepository
import ir.miare.domain.model.PlayerSortOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val local: PlayerLocalDatasource,
    private val remote: PlayerRemoteDatasource
) : PlayerRepository {

    override suspend fun getPlayers(
        sortBy: PlayerSortOption,
        offset: Long,
        limit: Int
    ): Resource<List<LeagueWithPlayers>> {
        return catching {
            val response = remote.getPlayers(
                sortBy = sortBy,
                offset = offset,
                limit = limit
            )

            val followedIds = local.getFollowedPlayersIds().first().toSet()

            response.map { leagueWithPlayers ->
                leagueWithPlayers.copy(
                    players = leagueWithPlayers.players.map { player ->
                        if (player.id in followedIds) {
                            player.copy(followed = true)
                        } else player
                    }
                )
            }
        }
    }

    override fun getFollowedPlayers(sortBy: PlayerSortOption): Flow<Resource<List<LeagueWithPlayers>>> {
        return local.getFollowedPlayers().asResourceFlow()
    }

    override fun getFollowedPlayersIds(): Flow<Resource<List<PlayerId>>> {
        return local.getFollowedPlayersIds().asResourceFlow()
    }

    override suspend fun follow(player: PlayerWithLeague): Resource<Unit> {
        return catching { local.follow(player = player) }
    }

    override suspend fun unfollow(player: PlayerWithLeague): Resource<Unit> {
        return catching { local.unfollow(player = player) }
    }

}