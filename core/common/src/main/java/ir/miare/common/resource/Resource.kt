package ir.miare.common.resource

import ir.miare.common.exception.DomainException

sealed class Resource<out T> {

    data class Failure(val exception: DomainException) : Resource<Nothing>()

    data class Success<out T>(val data: T) : Resource<T>()

    fun exceptionOrNull(): Throwable? {
        return when (this) {
            is Failure -> exception
            else -> null
        }
    }

    override fun toString(): String {
        return when (this) {
            is Failure -> "Failure($exception)"
            is Success -> "Success($data)"
        }
    }

}

inline fun <R, T : R> Resource<T>.getOrElse(onFailure: (DomainException) -> R): R {
    return when (this) {
        is Resource.Success -> data
        else -> onFailure((this as? Resource.Failure)?.exception ?: DomainException.Unknown)
    }
}

inline fun <R, T> Resource<T>.map(transform: (value: T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(data = transform(data))
        is Resource.Failure -> this
    }
}

inline fun <T> Resource<T>.onFailure(action: (DomainException) -> Unit): Resource<T> {
    if (this is Resource.Failure) action(exception)
    return this
}

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}