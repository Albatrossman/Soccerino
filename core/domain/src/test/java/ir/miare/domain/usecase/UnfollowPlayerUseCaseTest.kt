package ir.miare.domain.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import ir.miare.common.exception.DomainException
import ir.miare.common.resource.Resource
import ir.miare.domain.model.League
import ir.miare.domain.model.Player
import ir.miare.domain.model.PlayerWithLeague
import ir.miare.domain.model.Team
import ir.miare.domain.model.id.LeagueId
import ir.miare.domain.model.id.PlayerId
import ir.miare.domain.repo.PlayerRepository
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException

class UnfollowPlayerUseCaseTest {

    private val repository: PlayerRepository = mockk()
    private lateinit var useCase: UnfollowPlayerUseCase

    @Before
    fun setUp() {
        useCase = UnfollowPlayerUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke when repository returns Success then returns Success and forwards player param`() = runTest {
        val playerWithLeague = samplePlayerWithLeague(leagueId = "L1", playerId = "P1")
        val success: Resource<Unit> = Resource.Success(data = Unit)

        coEvery { repository.unfollow(player = playerWithLeague) } returns success

        val result = useCase(player = playerWithLeague)

        result shouldBe success
        coVerify(exactly = 1) { repository.unfollow(player = playerWithLeague) }
    }

    @Test
    fun `invoke when repository returns Failure then returns same Failure`() = runTest {
        val playerWithLeague = samplePlayerWithLeague(leagueId = "L2", playerId = "P2")
        val domainEx = DomainException.Unknown
        val failure: Resource<Unit> = Resource.Failure(domainEx)

        coEvery { repository.unfollow(playerWithLeague) } returns failure

        val result = useCase(playerWithLeague)

        result shouldBe failure
        result.shouldBeInstanceOf<Resource.Failure>()
        (result as Resource.Failure).exception shouldBe domainEx

        coVerify(exactly = 1) { repository.unfollow(playerWithLeague) }
    }

    @Test
    fun `invoke when repository throws CancellationException then CancellationException is propagated`() = runTest {
        val playerWithLeague = samplePlayerWithLeague(leagueId = "L3", playerId = "P3")

        coEvery { repository.unfollow(player = playerWithLeague) } throws CancellationException("cancelled")

        shouldThrow<CancellationException> {
            useCase(player = playerWithLeague)
        }

        coVerify(exactly = 1) { repository.unfollow(player = playerWithLeague) }
    }

    @Test
    fun `invoke when repository throws other exception then exception is propagated`() = runTest {
        val playerWithLeague = samplePlayerWithLeague(leagueId = "L4", playerId = "P4")
        val ex = IllegalStateException("boom")

        coEvery { repository.unfollow(player = playerWithLeague) } throws ex

        shouldThrow<IllegalStateException> {
            useCase(player = playerWithLeague)
        }

        coVerify(exactly = 1) { repository.unfollow(player = playerWithLeague) }
    }

    private fun samplePlayerWithLeague(leagueId: String = "L", playerId: String = "P"): PlayerWithLeague {
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
            totalGoals = 3,
            team = Team(name = "Team-$leagueId", rank = 1),
            followed = true
        )

        return PlayerWithLeague(league = league, player = player)
    }

}