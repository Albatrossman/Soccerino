package ir.miare.domain.model.id

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class LeagueId(val value: String) {

    override fun toString(): String {
        return value
    }

}