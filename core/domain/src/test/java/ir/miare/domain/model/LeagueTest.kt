package ir.miare.domain.model

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ir.miare.domain.model.id.LeagueId
import org.junit.Test

class LeagueTest {

    @Test
    fun `should create League with valid parameters`() {
        val leagueId = LeagueId(value = "L0")
        val league = League(
            id = leagueId,
            name = "Premier League",
            country = "England",
            rank = 1,
            totalMatches = 380
        )

        league.id shouldBe leagueId
        league.name shouldBe "Premier League"
        league.country shouldBe "England"
        league.rank shouldBe 1
        league.totalMatches shouldBe 380
    }

    @Test
    fun `equality should be based on id`() {
        val leagueId = LeagueId(value = "L0")
        val league1 = League(
            id = leagueId,
            name = "Premier League",
            country = "England",
            rank = 1,
            totalMatches = 380
        )
        val league2 = League(
            id = leagueId,
            name = "Different Name",
            country = "Different",
            rank = 2,
            totalMatches = 100
        )

        league1 shouldBe league2
        league1.hashCode() shouldBe league2.hashCode()

        val league3 = League(
            id = LeagueId(value = "L1"),
            name = "Premier League",
            country = "England",
            rank = 1,
            totalMatches = 380
        )

        league1 shouldNotBe league3
    }

}