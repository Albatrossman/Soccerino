package ir.miare.data.mapper

import ir.miare.data.model.LeagueWithPlayersDto
import ir.miare.domain.model.LeagueWithPlayers

fun LeagueWithPlayersDto.domain(): LeagueWithPlayers {
    return LeagueWithPlayers(
        league = this.league.domain(),
        players = this.players.map { player -> player.domain() }
    )
}