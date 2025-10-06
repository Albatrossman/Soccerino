package ir.miare.domain.model.id

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class PlayerId(val value: String) {

    override fun toString(): String {
        return value
    }

}