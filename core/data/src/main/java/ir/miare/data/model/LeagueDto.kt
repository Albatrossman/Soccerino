package ir.miare.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeagueDto(
    val name: String,
    val country: String,
    val rank: Int,
    @SerialName(value = "total_matches")
    val totalMatches: Int
)