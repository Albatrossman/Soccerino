package ir.miare.domain.model

import ir.miare.domain.model.id.LeagueId

data class League(
    val id: LeagueId,
    val name: String,
    val country: String,
    val rank: Int,
    val totalMatches: Int
) {

    override fun equals(other: Any?): Boolean {
        return other is League && id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}