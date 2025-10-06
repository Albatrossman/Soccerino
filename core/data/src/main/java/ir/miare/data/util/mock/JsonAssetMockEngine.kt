package ir.miare.data.util.mock

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import ir.miare.common.util.DispatcherProvider
import ir.miare.data.model.LeagueWithPlayersDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.time.Duration.Companion.milliseconds

class JsonAssetMockEngine(
    private val dispatchers: DispatcherProvider,
    private val json: Json,
    private val parser: JsonAssetParser,
    private val assetFileName: String,
) {

    fun build(): MockEngine {
        return MockEngine { request ->
            when (request.url.encodedPath) {
                PATH_GET_PLAYERS -> {
                    if (request.method != HttpMethod.Get) {
                        return@MockEngine respondNotFount()
                    }

                    val offset = request.url.parameters["offset"]?.toLongOrNull()?.coerceAtLeast(minimumValue = 0L) ?: 0
                    val limit = request.url.parameters["limit"]?.toIntOrNull()?.coerceAtLeast(minimumValue = 1) ?: 10
                    val sortOption = request.url.parameters["sort"] ?: "default"

                    val leaguesWithPlayers: List<LeagueWithPlayersDto> = try {
                        parseLeaguesWithPlayers()
                    } catch (throwable: Throwable) {
                        return@MockEngine respondError(
                            message = throwable.message ?: "Something went wrong!"
                        )
                    }

                    val response = leaguesWithPlayers.sort(sortBy = sortOption).page(offset = offset, limit = limit)

                    delay(duration = 500.milliseconds)

                    return@MockEngine respond(
                        content = json.encodeToString(value = response),
                        status = HttpStatusCode.OK,
                        headers = headersOf(
                            name = "Content-Type",
                            value = ContentType.Application.Json.toString()
                        )
                    )
                }
                else -> return@MockEngine respondNotFount()
            }
        }
    }

    private fun List<LeagueWithPlayersDto>.page(offset: Long, limit: Int): List<LeagueWithPlayersDto> {
        val fromIndex: Long = offset.coerceAtLeast(minimumValue = 0)
        val toIndexExclusive: Long = (fromIndex + limit).coerceAtMost(maximumValue = this.size.toLong())

        return if (fromIndex >= this.size || this.isEmpty()) {
            emptyList()
        } else {
            this.subList(
                fromIndex = fromIndex.toInt(),
                toIndex = toIndexExclusive.toInt()
            )
        }
    }

    private fun List<LeagueWithPlayersDto>.sort(sortBy: String): List<LeagueWithPlayersDto> {
        return when (sortBy) {
            "team_and_league_ranking" -> {
                this.sortedBy { it.league.rank }.map { league ->
                    league.copy(
                        players = league.players.sortedBy { player ->
                            player.team.rank
                        }
                    )
                }
            }
            "most_goals_scored" -> {
                this.map { league ->
                    league to league.players.sumOf { it.totalGoals }
                }.sortedByDescending { (_, totalGoals) -> totalGoals }.map { (league, _) ->
                    league.copy(
                        players = league.players.sortedByDescending { it.totalGoals }
                    )
                }
            }
            "average_goals_scored" -> {
                this.map { league ->
                    val totalGoals = league.players.sumOf { it.totalGoals }
                    val averageGoals = if (league.league.totalMatches > 0) {
                        totalGoals.toDouble() / league.league.totalMatches
                    } else {
                        totalGoals.toDouble()
                    }

                    Triple(first = league, second = averageGoals, third = totalGoals)
                }.sortedByDescending { (_, avgGoals, _) -> avgGoals }.map { (league, _, _) ->
                    league.copy(
                        players = league.players.sortedByDescending { it.totalGoals }
                    )
                }
            }
            else -> this
        }
    }

    private suspend fun parseLeaguesWithPlayers(): List<LeagueWithPlayersDto> {
        return withContext(context = dispatchers.io) {
            val serializer: KSerializer<LeagueWithPlayersDto> = LeagueWithPlayersDto.serializer()

            parser.parse(
                fileName = assetFileName,
                deserializer = ListSerializer(elementSerializer = serializer)
            )
        }
    }

    private fun MockRequestHandleScope.respondNotFount(): HttpResponseData {
        return respondError(
            status = HttpStatusCode.NotFound,
            message = "Not Found"
        )
    }

    private fun MockRequestHandleScope.respondError(
        status: HttpStatusCode = HttpStatusCode.InternalServerError,
        message: String = "Something went wrong!"
    ): HttpResponseData {
        val content = buildJsonObject {
            put(key = "message", value = message)
        }.toString()

        return respond(
            content = content,
            status = status,
            headers = headersOf(
                name = "Content-Type",
                value = ContentType.Application.Json.toString()
            )
        )
    }

    private companion object {

        const val PATH_GET_PLAYERS: String = "/list"

    }

}