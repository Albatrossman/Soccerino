package ir.miare.domain.usecase

import ir.miare.common.resource.Resource
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.repo.PlayerRepository
import ir.miare.domain.model.PlayerSortOption
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFollowedPlayersUseCase @Inject constructor(private val repository: PlayerRepository) {

    operator fun invoke(sortBy: PlayerSortOption): Flow<Resource<List<LeagueWithPlayers>>> {
        return repository.getFollowedPlayers(sortBy = sortBy)
    }

}