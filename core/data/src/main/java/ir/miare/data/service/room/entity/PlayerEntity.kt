package ir.miare.data.service.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followed_players")
data class PlayerEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "total_goals")
    val totalGoals: Int,
    @ColumnInfo(name = "team_name")
    val teamName: String,
    @ColumnInfo(name = "team_rank")
    val teamRank: Int,
    @ColumnInfo(name = "league_id")
    val leagueId: String,
    @ColumnInfo(name = "league_name")
    val leagueName: String,
    @ColumnInfo(name = "league_country")
    val leagueCountry: String,
    @ColumnInfo(name = "league_rank")
    val leagueRank: Int,
    @ColumnInfo(name = "league_total_matches")
    val leagueTotalMatches: Int
)