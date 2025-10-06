package ir.miare.domain.usecase

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import ir.miare.common.resource.Resource
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.repo.PlayerRepository
import ir.miare.domain.model.PlayerSortOption
import kotlinx.coroutines.test.runTest
import org.junit.Test
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coVerify
import io.mockk.unmockkAll
import ir.miare.common.exception.DomainException
import ir.miare.domain.model.League
import ir.miare.domain.model.Player
import ir.miare.domain.model.Team
import ir.miare.domain.model.id.LeagueId
import ir.miare.domain.model.id.PlayerId
import org.junit.After
import org.junit.Before

class GetPlayersUseCaseTest {

    private val repository: PlayerRepository = mockk()
    private lateinit var useCase: GetPlayersUseCase

    @Before
    fun setup() {
        useCase = GetPlayersUseCase(repository)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `invoke - returns success from repository and forwards default params`() = runTest {
        val expectedList = listOf(sampleLeagueWithPlayers(id = "L1", playerId = "P1"))
        val expectedResource: Resource<List<LeagueWithPlayers>> = Resource.Success(expectedList)

        coEvery {
            repository.getPlayers(
                sortBy = PlayerSortOption.DEFAULT,
                offset = 0L,
                limit = 2
            )
        } returns expectedResource

        val result = useCase()

        result shouldBe expectedResource

        coVerify(exactly = 1) {
            repository.getPlayers(
                sortBy = PlayerSortOption.DEFAULT,
                offset = 0L,
                limit = 2
            )
        }
    }

    @Test
    fun `invoke - propagates failure resource from repository`() = runTest {
        val domainException = DomainException.Unknown
        val failure: Resource<List<LeagueWithPlayers>> = Resource.Failure(domainException)

        coEvery {
            repository.getPlayers(
                sortBy = PlayerSortOption.MOST_GOALS_SCORED,
                offset = 10L,
                limit = 5
            )
        } returns failure

        val result = useCase(sortBy = PlayerSortOption.MOST_GOALS_SCORED, offset = 10L, limit = 5)

        result shouldBe failure
        result.shouldBeInstanceOf<Resource.Failure>()
        (result as Resource.Failure).exception shouldBe domainException

        coVerify(exactly = 1) {
            repository.getPlayers(
                sortBy = PlayerSortOption.MOST_GOALS_SCORED,
                offset = 10L,
                limit = 5
            )
        }
    }

    @Test
    fun `invoke - forwards custom parameters to repository`() = runTest {
        val expectedList = listOf(
            sampleLeagueWithPlayers(id = "L2", playerId = "P2"),
            sampleLeagueWithPlayers(id = "L3", playerId = "P3")
        )
        val success: Resource<List<LeagueWithPlayers>> = Resource.Success(expectedList)

        val sortBy = PlayerSortOption.TEAM_LEAGUE_RANKING
        val offset = 123L
        val limit = 7

        coEvery {
            repository.getPlayers(sortBy = sortBy, offset = offset, limit = limit)
        } returns success

        val result = useCase(sortBy = sortBy, offset = offset, limit = limit)

        result shouldBe success
        coVerify(exactly = 1) {
            repository.getPlayers(sortBy = sortBy, offset = offset, limit = limit)
        }
    }

    private fun sampleLeagueWithPlayers(
        id: String = "L",
        playerId: String = "P"
    ): LeagueWithPlayers {
        val league = League(
            id = LeagueId(value = id),
            name = "League $id",
            country = "Country-$id",
            rank = 1,
            totalMatches = 10
        )

        val player = Player(
            id = PlayerId(value = playerId),
            name = "Player $playerId",
            totalGoals = 5,
            team = Team(name = "Team-$id", rank = 1),
            followed = false
        )

        return LeagueWithPlayers(
            league = league,
            players = listOf(player)
        )
    }

}