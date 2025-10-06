package ir.miare.common.logger

interface Logger {

    fun v(tag: String? = null, message: String?, vararg args: Any?)

    fun v(tag: String? = null, throwable: Throwable?, message: String?, vararg args: Any?)

    fun v(tag: String? = null, throwable: Throwable?)

    fun d(tag: String? = null, message: String?, vararg args: Any?)

    fun d(tag: String? = null, throwable: Throwable?, message: String?, vararg args: Any?)

    fun d(tag: String? = null, throwable: Throwable?)

    fun i(tag: String? = null, message: String?, vararg args: Any?)

    fun i(tag: String? = null, throwable: Throwable?, message: String?, vararg args: Any?)

    fun i(tag: String? = null, throwable: Throwable?)

    fun w(tag: String? = null, message: String?, vararg args: Any?)

    fun w(tag: String? = null, throwable: Throwable?, message: String?, vararg args: Any?)

    fun w(tag: String? = null, throwable: Throwable?)

    fun e(tag: String? = null, message: String?, vararg args: Any?)

    fun e(tag: String? = null, throwable: Throwable?, message: String?, vararg args: Any?)

    fun e(tag: String? = null, throwable: Throwable?)

}