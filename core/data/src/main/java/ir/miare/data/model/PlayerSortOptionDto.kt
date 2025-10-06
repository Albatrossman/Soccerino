package ir.miare.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PlayerSortOptionDto {
    @SerialName(value = "default")
    DEFAULT,
    @SerialName(value = "team_and_league_ranking")
    TEAM_LEAGUE_RANKING,
    @SerialName(value = "most_goals_scored")
    MOST_GOALS_SCORED,
    @SerialName(value = "average_goals_scored")
    AVG_GOAL_PER_MATCH;

    val serialName: String
        get() = this::class.java
            .getField(this.name)
            .getAnnotation(SerialName::class.java)?.value ?: this.name.lowercase()
}