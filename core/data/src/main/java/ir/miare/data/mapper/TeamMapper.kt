package ir.miare.data.mapper

import ir.miare.data.model.TeamDto
import ir.miare.domain.model.Team

fun TeamDto.domain(): Team {
    return Team(
        name = this.name,
        rank = this.rank
    )
}