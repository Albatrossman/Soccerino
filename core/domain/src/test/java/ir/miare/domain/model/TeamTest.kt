package ir.miare.domain.model

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ir.miare.domain.model.id.LeagueId
import ir.miare.domain.model.id.TeamId
import org.junit.Test

class TeamTest {

    @Test
    fun `should create Team with valid parameters`() {
        val teamId = TeamId(value = "T0")
        val leagueId = LeagueId(value = "L0")
        val team = Team(
            id = teamId,
            name = "Team A",
            rank = 1,
            leagueId = leagueId
        )

        team.id shouldBe teamId
        team.name shouldBe "Team A"
        team.rank shouldBe 1
        team.leagueId shouldBe leagueId
    }

    @Test
    fun `equality should be based on id`() {
        val teamId = TeamId(value = "T0")
        val leagueId = LeagueId(value = "L0")

        val team1 = Team(
            id = teamId,
            name = "Team A",
            rank = 1,
            leagueId = leagueId
        )
        val team2 = Team(
            id = teamId,
            name = "Another Team A",
            rank = 2,
            leagueId = leagueId
        )

        team1 shouldBe team2
        team1.hashCode() shouldBe team2.hashCode()

        val team3 = Team(
            id = TeamId(value = "T1"),
            name = "Team A",
            rank = 1,
            leagueId = leagueId
        )

        team1 shouldNotBe team3
    }

}