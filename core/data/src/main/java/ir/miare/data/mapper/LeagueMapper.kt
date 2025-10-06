package ir.miare.data.mapper

import ir.miare.data.model.LeagueDto
import ir.miare.domain.model.League
import ir.miare.domain.model.id.LeagueId

fun LeagueDto.domain(): League {
    return League(
        id = LeagueId(value = "${this.name}.${this.country}"),
        name = this.name,
        country = this.country,
        rank = this.rank,
        totalMatches = this.totalMatches
    )
}