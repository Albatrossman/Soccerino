package ir.miare.domain.usecase

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import ir.miare.common.exception.DomainException
import ir.miare.common.resource.Resource
import ir.miare.domain.model.League
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.Player
import ir.miare.domain.model.PlayerSortOption
import ir.miare.domain.model.Team
import ir.miare.domain.model.id.LeagueId
import ir.miare.domain.model.id.PlayerId
import ir.miare.domain.repo.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetFollowedPlayersUseCaseTest {

    private val repository: PlayerRepository = mockk()
    private lateinit var useCase: GetFollowedPlayersUseCase

    @Before
    fun setUp() {
        useCase = GetFollowedPlayersUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke when repository emits single success with items then flow emits same success`() = runTest {
        val sample = listOf(sampleLeagueWithPlayers("L1", "P1"))
        val resource: Resource<List<LeagueWithPlayers>> = Resource.Success(sample)
        val sortBy = PlayerSortOption.DEFAULT

        every { repository.getFollowedPlayers(sortBy) } returns flowOf(resource)

        useCase(sortBy).test {
            val item = awaitItem()
            item shouldBe resource
            awaitComplete()
        }

        verify(exactly = 1) { repository.getFollowedPlayers(sortBy) }
    }

    @Test
    fun `invoke when repository emits single success with empty list then flow emits empty success`() = runTest {
        val emptyResource: Resource<List<LeagueWithPlayers>> = Resource.Success(emptyList())
        val sortBy = PlayerSortOption.AVG_GOAL_PER_MATCH

        every { repository.getFollowedPlayers(sortBy) } returns flowOf(emptyResource)

        useCase(sortBy).test {
            val item = awaitItem()
            item shouldBe emptyResource
            (item as Resource.Success).data shouldBe emptyList()
            awaitComplete()
        }

        verify(exactly = 1) { repository.getFollowedPlayers(sortBy) }
    }

    @Test
    fun `invoke when repository emits multiple updates then flow emits them in order`() = runTest {
        val first = Resource.Success(listOf(sampleLeagueWithPlayers("L1", "P1")))
        val second = Resource.Success(listOf(sampleLeagueWithPlayers("L2", "P2"), sampleLeagueWithPlayers("L3", "P3")))
        val sortBy = PlayerSortOption.TEAM_LEAGUE_RANKING

        val updates: Flow<Resource<List<LeagueWithPlayers>>> = flow {
            emit(value = first)
            emit(value = second)
        }

        every { repository.getFollowedPlayers(sortBy) } returns updates

        useCase(sortBy).test {
            awaitItem() shouldBe first
            awaitItem() shouldBe second
            awaitComplete()
        }

        verify(exactly = 1) { repository.getFollowedPlayers(sortBy) }
    }

    @Test
    fun `invoke when repository emits failure then flow emits failure with same exception`() = runTest {
        val domainEx = DomainException.Unknown
        val failure: Resource<List<LeagueWithPlayers>> = Resource.Failure(domainEx)
        val sortBy = PlayerSortOption.MOST_GOALS_SCORED

        every { repository.getFollowedPlayers(sortBy) } returns flowOf(failure)

        useCase(sortBy).test {
            val item = awaitItem()
            item.shouldBeInstanceOf<Resource.Failure>()
            (item as Resource.Failure).exception shouldBe domainEx
            awaitComplete()
        }

        verify(exactly = 1) { repository.getFollowedPlayers(sortBy) }
    }

    private fun sampleLeagueWithPlayers(leagueId: String = "L", playerId: String = "P"): LeagueWithPlayers {
        val league = League(
            id = LeagueId(value = leagueId),
            name = "League $leagueId",
            country = "Country-$leagueId",
            rank = 1,
            totalMatches = 10
        )

        val player = Player(
            id = PlayerId(value = playerId),
            name = "Player $playerId",
            totalGoals = 5,
            team = Team(name = "Team-$leagueId", rank = 1),
            followed = true
        )

        return LeagueWithPlayers(
            league = league,
            players = listOf(player)
        )
    }

}