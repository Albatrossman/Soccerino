package ir.miare.domain.model

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ir.miare.domain.model.id.PlayerId
import ir.miare.domain.model.id.TeamId
import org.junit.Test

class PlayerTest {

    @Test
    fun `should create Player with valid parameters`() {
        val playerId = PlayerId(value = "P0")
        val teamId = TeamId(value = "T0")
        val player = Player(
            id = playerId,
            name = "John",
            totalGoals = 5,
            followed = false,
            teamId = teamId
        )

        player.id shouldBe playerId
        player.name shouldBe "John"
        player.totalGoals shouldBe 5
        player.followed shouldBe false
        player.teamId shouldBe teamId
    }

    @Test
    fun `equality should be based on id`() {
        val playerId = PlayerId(value = "P0")
        val teamId = TeamId(value = "T0")

        val player1 = Player(
            id = playerId,
            name = "John",
            totalGoals = 5,
            followed = false,
            teamId = teamId,
        )
        val player2 = Player(
            id = playerId,
            name = "John Updated",
            totalGoals = 7,
            followed = true,
            teamId = teamId
        )

        player1 shouldBe player2
        player1.hashCode() shouldBe player2.hashCode()

        val player3 = Player(
            id = PlayerId(value = "P1"),
            name = "John",
            totalGoals = 5,
            teamId = teamId
        )

        player1 shouldNotBe player3
    }

}