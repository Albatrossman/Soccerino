package ir.miare.domain.model

import ir.miare.domain.model.id.LeagueId
import kotlinx.serialization.Serializable

@Serializable
data class League(
    val id: LeagueId,
    val name: String,
    val country: String,
    val rank: Int,
    val totalMatches: Int
)