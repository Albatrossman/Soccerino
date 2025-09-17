package ir.miare.domain.model.id

@JvmInline
value class PlayerId(val value: String) {

    override fun toString(): String {
        return value
    }

}