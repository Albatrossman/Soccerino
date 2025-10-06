package ir.miare.ui.mapper

import ir.miare.domain.model.League
import ir.miare.domain.model.Player
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.domain.model.Team
import ir.miare.domain.model.id.LeagueId
import ir.miare.domain.model.id.PlayerId
import ir.miare.ui.util.navigation.Screen

fun PlayerWithLeague.navigation(): Screen.Player {
    return Screen.Player(
        id = this.player.id.value,
        name = this.player.name,
        goals = this.player.totalGoals,
        teamName = this.player.team.name,
        teamRank = this.player.team.rank,
        followed = this.player.followed,
        leagueId = this.league.id.value,
        leagueName = this.league.name,
        leagueCountry = this.league.country,
        leagueRank = this.league.rank,
        leagueTotalMatches = this.league.totalMatches
    )
}

fun Screen.Player.domain(): PlayerWithLeague {
    return PlayerWithLeague(
        player = Player(
            id = PlayerId(value = this.id),
            name = this.name,
            totalGoals = this.goals,
            team = Team(
                name = this.teamName,
                rank = this.teamRank
            ),
            followed = this.followed
        ),
        league = League(
            id = LeagueId(value = this.leagueId),
            name = this.leagueName,
            country = this.leagueCountry,
            rank = this.leagueRank,
            totalMatches = this.leagueTotalMatches
        )
    )
}