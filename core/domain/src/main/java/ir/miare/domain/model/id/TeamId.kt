package ir.miare.domain.model.id

@JvmInline
value class TeamId(val value: String) {

    override fun toString(): String {
        return value
    }

}