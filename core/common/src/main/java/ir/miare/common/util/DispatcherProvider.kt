package ir.miare.common.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
    val io: CoroutineDispatcher

}