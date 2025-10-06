package ir.miare.data.mapper

import ir.miare.data.model.PlayerDto
import ir.miare.domain.model.Player
import ir.miare.domain.model.id.PlayerId

fun PlayerDto.domain(): Player {
    return Player(
        id = PlayerId(value = "${this.name}.${this.team}"),
        name = this.name,
        totalGoals = this.totalGoals,
        team = this.team.domain()
    )
}

