package ir.miare.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerWithLeague(
    val player: Player,
    val league: League
)