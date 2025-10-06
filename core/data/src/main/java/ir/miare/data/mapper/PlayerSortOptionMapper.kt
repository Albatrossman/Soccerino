package ir.miare.data.mapper

import ir.miare.data.model.PlayerSortOptionDto
import ir.miare.domain.model.PlayerSortOption

fun PlayerSortOption.dto(): PlayerSortOptionDto {
    return when (this) {
        PlayerSortOption.DEFAULT -> PlayerSortOptionDto.DEFAULT
        PlayerSortOption.TEAM_LEAGUE_RANKING -> PlayerSortOptionDto.TEAM_LEAGUE_RANKING
        PlayerSortOption.MOST_GOALS_SCORED -> PlayerSortOptionDto.MOST_GOALS_SCORED
        PlayerSortOption.AVG_GOAL_PER_MATCH -> PlayerSortOptionDto.AVG_GOAL_PER_MATCH
    }
}