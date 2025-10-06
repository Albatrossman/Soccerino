package ir.miare.following

import ir.miare.domain.model.League
import ir.miare.domain.model.Player

internal sealed class FollowingScreenEvent {

    data class OnPlayerClick(val player: Player, val league: League) : FollowingScreenEvent()

}