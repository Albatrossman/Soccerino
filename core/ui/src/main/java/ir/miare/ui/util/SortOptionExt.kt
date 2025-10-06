package ir.miare.ui.util

import androidx.annotation.StringRes
import ir.miare.domain.model.PlayerSortOption
import ir.miare.ui.R

val PlayerSortOption.labelResId
    @StringRes
    get() = when (this) {
        PlayerSortOption.DEFAULT -> R.string.label_sort_player_default
        PlayerSortOption.TEAM_LEAGUE_RANKING -> R.string.label_sort_player_team_league_ranking
        PlayerSortOption.MOST_GOALS_SCORED -> R.string.label_sort_player_most_goals_scored
        PlayerSortOption.AVG_GOAL_PER_MATCH -> R.string.label_sort_player_average_goals_per_match
    }