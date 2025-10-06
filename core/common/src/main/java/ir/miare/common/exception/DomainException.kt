package ir.miare.common.exception

sealed class DomainException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {

    data object Unknown : DomainException(message = "Unknown error") {

        private fun readResolve(): Any = Unknown

    }

    data object Connection : DomainException(message = "Connection error") {

        private fun readResolve(): Any = Connection

    }

    data object Timeout : DomainException(message = "Timeout error") {

        private fun readResolve(): Any = Timeout

    }

    sealed class Server(val code: Int, val description: String?) : DomainException() {

        class Unauthorized(code: Int = 401, description: String?) : Server(code = code, description = description)

        class Forbidden(code: Int = 403, description: String?) : Server(code = code, description = description)

        class Dynamic(code: Int, description: String?) : Server(code = code, description = description)

    }

}