package ir.miare.domain.model

import ir.miare.domain.model.id.LeagueId
import ir.miare.domain.model.id.TeamId

data class Team(
    val id: TeamId,
    val name: String,
    val rank: Int,
    val leagueId: LeagueId
) {

    override fun equals(other: Any?): Boolean {
        return other is Team && id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}