package ir.miare.ui.util.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Ranking : Screen()

    @Serializable
    data object Following : Screen()

    @Serializable
    data class Player(
        val id: String,
        val name: String,
        val goals: Int,
        val teamName: String,
        val teamRank: Int,
        val followed: Boolean,
        val leagueId: String,
        val leagueName: String,
        val leagueCountry: String,
        val leagueRank: Int,
        val leagueTotalMatches: Int
    ) : Screen()

}