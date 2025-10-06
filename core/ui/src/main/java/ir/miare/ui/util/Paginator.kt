package ir.miare.ui.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Paginator<T>(
    private val scope: CoroutineScope,
    private val fetch: suspend (offset: Long, limit: Int) -> Collection<T>?,
    private val limit: Int = 2
) {

    private val mutex = Mutex()
    var offset: Long = 0L
        private set

    init {
        require(value = limit > 0) { "limit must be > 0" }
    }

    fun previous(): Job {
        return scope.launch {
            mutex.withLock {
                val result = fetch(offset, limit)
                offset = (offset - (result?.size ?: limit)).coerceAtLeast(minimumValue = 0)
            }
        }
    }

    fun next(): Job {
        return scope.launch {
            mutex.withLock {
                val result = fetch(offset, limit)
                offset += (result?.size ?: limit)
            }
        }
    }

    fun refresh(): Job {
        return scope.launch {
            mutex.withLock {
                offset = 0
                fetch(offset, limit)
            }
        }
    }

}