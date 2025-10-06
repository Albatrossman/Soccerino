package ir.miare.data.util

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import ir.miare.common.exception.DomainException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

suspend fun Throwable.toDomainException(): DomainException {
    return when (this) {
        is TimeoutCancellationException -> DomainException.Timeout
        is DomainException -> this
        is ResponseException -> {
            val statusCode = response.status.value
            val body = response.bodyAsText()
            val message = try {
                val json = Json.parseToJsonElement(string = body) as? JsonObject
                json?.get("message")?.jsonPrimitive?.content
            } catch (e: Exception) {
                null
            } ?: "HTTP error"

            when (statusCode) {
                401 -> DomainException.Server.Unauthorized(
                    code = statusCode,
                    description = message
                )
                403 -> DomainException.Server.Forbidden(
                    code = statusCode,
                    description = message
                )
                else -> DomainException.Server.Dynamic(
                    code = statusCode,
                    description = message
                )
            }
        }
        else -> DomainException.Unknown
    }
}