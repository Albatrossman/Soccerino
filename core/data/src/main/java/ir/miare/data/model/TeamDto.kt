package ir.miare.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TeamDto(
    val name: String,
    val rank: Int
)
