package ir.miare.data.util

import ir.miare.common.logger.Log
import ir.miare.common.resource.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun <R> catching(block: suspend () -> R): Resource<R> {
    val logMessagePrefix = "catching"

    return runCatching { block() }
        .fold(
            onSuccess = {
                Log.i(tag = null, message = "$logMessagePrefix -> $it")
                Resource.Success(data = it)
            },
            onFailure = { throwable ->
                if (throwable is CancellationException) throw throwable
                Log.e(tag = null, message = "$logMessagePrefix -> ${throwable.toString()}")
                Resource.Failure(exception = throwable.toDomainException())
            }
        )
}

fun <R> Flow<R>.asResourceFlow(): Flow<Resource<R>> = flow {
    val logMessagePrefix = "Flow<R>.asResourceFlow()"

    try {
        collect { value ->
            Log.i(tag = null, message = "$logMessagePrefix -> $value")
            emit(value = Resource.Success(data = value))
        }
    } catch (throwable: Throwable) {
        if (throwable is CancellationException) throw throwable
        Log.e(tag = null, message = "$logMessagePrefix -> ${throwable.toString()}")
        emit(value = Resource.Failure(exception = throwable.toDomainException()))
    }
}