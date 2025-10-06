package ir.miare.data.mapper

import ir.miare.data.service.room.entity.PlayerEntity
import ir.miare.domain.model.League
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.Player
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.domain.model.Team
import ir.miare.domain.model.id.LeagueId
import ir.miare.domain.model.id.PlayerId
import kotlin.collections.component1
import kotlin.collections.component2

fun List<PlayerEntity>.domain(): List<LeagueWithPlayers> {
    return this
        .groupBy { it.leagueId }
        .takeIf { it.isNotEmpty() }?.map { (leagueId, playersEntities) ->
            LeagueWithPlayers(
                league = League(
                    id = LeagueId(value = leagueId),
                    name = playersEntities.first().leagueName,
                    country = playersEntities.first().leagueCountry,
                    rank = playersEntities.first().leagueRank,
                    totalMatches = playersEntities.first().leagueTotalMatches
                ),
                players = playersEntities.map { entity ->
                    Player(
                        id = PlayerId(value = entity.id),
                        name = entity.name,
                        totalGoals = entity.totalGoals,
                        team = Team(
                            name = entity.teamName,
                            rank = entity.teamRank
                        ),
                        followed = true
                    )
                }
            )
        } ?: emptyList()
}

fun PlayerWithLeague.entity(): PlayerEntity {
    return PlayerEntity(
        id = this.player.id.value,
        name = this.player.name,
        totalGoals = this.player.totalGoals,
        teamName = this.player.team.name,
        teamRank = this.player.team.rank,
        leagueId = this.league.id.value,
        leagueName = this.league.name,
        leagueCountry = this.league.country,
        leagueRank = this.league.rank,
        leagueTotalMatches = this.league.totalMatches
    )
}