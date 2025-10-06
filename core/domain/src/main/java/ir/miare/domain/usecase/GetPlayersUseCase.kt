package ir.miare.domain.usecase

import ir.miare.common.resource.Resource
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.repo.PlayerRepository
import ir.miare.domain.model.PlayerSortOption
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(private val repository: PlayerRepository) {

    suspend operator fun invoke(
        sortBy: PlayerSortOption = PlayerSortOption.DEFAULT,
        offset: Long = 0,
        limit: Int = 2
    ): Resource<List<LeagueWithPlayers>> {
        return repository.getPlayers(
            sortBy = sortBy,
            offset = offset,
            limit = limit
        )
    }

}