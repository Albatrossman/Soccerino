package ir.miare.ranking

import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.PlayerSortOption

internal data class RankingScreenState(
    val loading: Boolean = false,
    val refreshing: Boolean = false,
    val sortOption: PlayerSortOption = PlayerSortOption.DEFAULT,
    val players: List<LeagueWithPlayers> = emptyList(),
    val loadingNextPage: Boolean = false,
    val endReached: Boolean = false
)
