package ir.miare.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val name: String,
    @SerialName(value = "total_goal")
    val totalGoals: Int,
    val team: TeamDto
)
