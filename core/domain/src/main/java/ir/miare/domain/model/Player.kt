package ir.miare.domain.model

import ir.miare.domain.model.id.PlayerId
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: PlayerId,
    val name: String,
    val totalGoals: Int,
    val team: Team,
    val followed: Boolean = false,
)
