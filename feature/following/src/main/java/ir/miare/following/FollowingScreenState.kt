package ir.miare.following

import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.PlayerSortOption

internal data class FollowingScreenState(
    val loading: Boolean = false,
    val sortOption: PlayerSortOption = PlayerSortOption.DEFAULT,
    val players: List<LeagueWithPlayers> = emptyList(),
)
