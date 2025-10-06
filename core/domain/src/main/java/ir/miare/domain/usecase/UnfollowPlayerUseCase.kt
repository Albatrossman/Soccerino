package ir.miare.domain.usecase

import ir.miare.common.resource.Resource
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.domain.repo.PlayerRepository
import javax.inject.Inject

class UnfollowPlayerUseCase @Inject constructor(private val repository: PlayerRepository) {

    suspend operator fun invoke(player: PlayerWithLeague): Resource<Unit> {
        return repository.unfollow(player = player)
    }

}