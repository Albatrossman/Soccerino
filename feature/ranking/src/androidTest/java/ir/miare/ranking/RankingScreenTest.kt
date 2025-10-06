package ir.miare.ranking

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import io.mockk.verify
import ir.miare.ui.R
import ir.miare.domain.model.League
import ir.miare.domain.model.LeagueWithPlayers
import ir.miare.domain.model.Player
import ir.miare.domain.model.PlayerSortOption
import ir.miare.domain.model.Team
import ir.miare.domain.model.id.LeagueId
import ir.miare.domain.model.id.PlayerId
import ir.miare.ui.theme.AppTheme
import ir.miare.ui.util.TestTag
import ir.miare.ui.util.labelResId
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(value = AndroidJUnit4::class)
class RankingScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var onEvent: (RankingScreenEvent) -> Unit

    @Before
    fun setup() {
        onEvent = mockk(relaxed = true)
    }

    @Test
    fun displaysInitialLoadingIndicator_whenStateIsLoading() {
        val state = RankingScreenState(loading = true)

        composeTestRule.setContent {
            AppTheme {
                PlayersScreenContent(state = state, onEvent = onEvent)
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = TestTag.RANKING_SCREEN_LOADING)
            .assertIsDisplayed()
    }

    @Test
    fun displaysEmptyState_whenNoPlayersAndNotLoading() {
        val state = RankingScreenState(loading = false, players = emptyList())

        composeTestRule.setContent {
            AppTheme {
                PlayersScreenContent(state = state, onEvent = onEvent)
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = TestTag.EMPTY_STATE)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(text = composeTestRule.activity.getString(R.string.title_empty_state_ranking))
            .assertIsDisplayed()
    }

    @Test
    fun displaysLeaguesPlayersAndSortOption_whenPlayersAvailable() {
        val league = League(
            id = LeagueId(value = "League1"),
            name = "Premier League",
            country = "England",
            rank = 1,
            totalMatches = 380
        )
        val player1 = Player(
            id = PlayerId("Player1"),
            name = "Player One",
            totalGoals = 20,
            team = Team(name = "Team A", rank = 10),
            followed = false
        )
        val player2 = Player(
            id = PlayerId("Player2"),
            name = "Player Two",
            totalGoals = 15,
            team = Team(name = "Team B", rank = 20),
            followed = true
        )
        val leagueWithPlayers = LeagueWithPlayers(league = league, players = listOf(player1, player2))

        val state = RankingScreenState(
            loading = false,
            players = listOf(leagueWithPlayers),
            sortOption = PlayerSortOption.DEFAULT
        )

        composeTestRule.setContent {
            AppTheme {
                PlayersScreenContent(state = state, onEvent = onEvent)
            }
        }

        composeTestRule.onNodeWithText(text = "Premier League").assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "1").assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "Player One").assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "Team A").assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "10").assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "Player Two").assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "Team B").assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "20").assertIsDisplayed()

        val sortLabel = composeTestRule.activity.getString(PlayerSortOption.DEFAULT.labelResId)
        val sortText = composeTestRule.activity.getString(R.string.label_sort_by).plus(other = ":")
        composeTestRule.onNodeWithText(text = sortText).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = sortLabel).assertIsDisplayed()
    }

    @Test
    fun triggersOnPlayerClick_whenMoreButtonClicked() {
        val league = League(
            id = LeagueId(value = "League1"),
            name = "Premier League",
            country = "England",
            rank = 1,
            totalMatches = 380
        )
        val player = Player(
            id = PlayerId(value = "Player1"),
            name = "Player One",
            totalGoals = 20,
            team = Team(name = "Team A", rank = 10),
            followed = false
        )
        val leagueWithPlayers = LeagueWithPlayers(league = league, players = listOf(player))
        val state = RankingScreenState(loading = false, players = listOf(leagueWithPlayers))

        composeTestRule.setContent {
            AppTheme {
                PlayersScreenContent(state = state, onEvent = onEvent)
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = "${TestTag.PLAYER_ROW_MORE_NAME}${player.name}")
            .performClick()

        verify { onEvent(RankingScreenEvent.OnPlayerClick(player = player, league = league)) }
    }

    @Test
    fun displaysRefreshingState_whenRefreshingIsTrue() {
        val state = RankingScreenState(refreshing = true)

        composeTestRule.setContent {
            AppTheme {
                PlayersScreenContent(state = state, onEvent = onEvent)
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = TestTag.RANKING_SCREEN_PULL_TO_REFRESH)
            .assertIsDisplayed()
    }

    @Test
    fun displaysLoadingNextPageIndicator_whenLoadingNextPageIsTrue() {
        val state = RankingScreenState(loadingNextPage = true)

        composeTestRule.setContent {
            AppTheme {
                PlayersScreenContent(state = state, onEvent = onEvent)
            }
        }

        composeTestRule.onNodeWithTag(TestTag.RANKING_SCREEN_LOADING_NEXT_PAGE)
            .assertIsDisplayed()
    }

    @Test
    fun triggersOnNextPageRequired_whenScrolledNearEnd() {
        val league = League(
            id = LeagueId(value = "League1"),
            name = "Premier League",
            country = "England",
            rank = 1,
            totalMatches = 380
        )
        val players = (1..12).map { i ->
            Player(
                id = PlayerId(value = "Player $i"),
                name = "Player $i",
                totalGoals = i,
                team = Team(name = "Team $i", rank = i),
                followed = false
            )
        }
        val leagueWithPlayers = LeagueWithPlayers(league = league, players = players)

        val state = RankingScreenState(
            loading = false,
            players = listOf(leagueWithPlayers),
            loadingNextPage = false,
            endReached = false,
            refreshing = false
        )

        composeTestRule.setContent {
            AppTheme {
                PlayersScreenContent(state = state, onEvent = onEvent)
            }
        }

        composeTestRule
            .onNodeWithTag(testTag = TestTag.RANKING_SCREEN_PLAYERS_LIST)
            .performScrollToIndex(index = 9)

        composeTestRule.waitUntil(timeoutMillis = 2000) {
            true
        }

        verify { onEvent(RankingScreenEvent.OnNextPageRequired) }
    }

}