package ir.miare.ranking

import ir.miare.domain.model.League
import ir.miare.domain.model.Player
import ir.miare.domain.model.PlayerSortOption

internal sealed class RankingScreenEvent {

    data object OnRefresh : RankingScreenEvent()

    data class OnSortOptionClick(val option: PlayerSortOption) : RankingScreenEvent()

    data class OnPlayerClick(val player: Player, val league: League) : RankingScreenEvent()

    data object OnNextPageRequired : RankingScreenEvent()

}