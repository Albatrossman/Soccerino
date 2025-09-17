package ir.miare.domain.model

import ir.miare.domain.model.id.PlayerId
import ir.miare.domain.model.id.TeamId

data class Player(
    val id: PlayerId,
    val name: String,
    val totalGoals: Int,
    val followed: Boolean = false,
    val teamId: TeamId
) {

    override fun equals(other: Any?): Boolean {
        return other is Player && id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
