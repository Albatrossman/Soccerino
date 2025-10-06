package ir.miare.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val name: String,
    val rank: Int
)