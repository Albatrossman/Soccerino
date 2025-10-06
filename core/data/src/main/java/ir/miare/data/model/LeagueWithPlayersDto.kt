package ir.miare.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LeagueWithPlayersDto(
    val league: LeagueDto,
    val players: List<PlayerDto>
)