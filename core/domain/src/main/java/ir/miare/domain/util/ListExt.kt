package ir.miare.domain.util

fun <T, K> List<T>.merge(
    incoming: List<T>,
    keySelector: (T) -> K
): List<T> {
    if (this.isEmpty()) return incoming
    if (incoming.isEmpty()) return this

    val existingKeys = this.map(transform = keySelector).toHashSet()
    val newItems = incoming.filter { keySelector(it) !in existingKeys }

    return this + newItems
}