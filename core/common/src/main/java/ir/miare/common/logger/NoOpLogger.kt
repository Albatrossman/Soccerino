package ir.miare.common.logger

internal class NoOpLogger : Logger {

    override fun v(tag: String?, message: String?, vararg args: Any?) {}

    override fun v(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun v(tag: String?, throwable: Throwable?) {}

    override fun d(tag: String?, message: String?, vararg args: Any?) {}

    override fun d(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun d(tag: String?, throwable: Throwable?) {}

    override fun i(tag: String?, message: String?, vararg args: Any?) {}

    override fun i(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun i(tag: String?, throwable: Throwable?) {}

    override fun w(tag: String?, message: String?, vararg args: Any?) {}

    override fun w(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun w(tag: String?, throwable: Throwable?) {}

    override fun e(tag: String?, message: String?, vararg args: Any?) {}

    override fun e(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {}

    override fun e(tag: String?, throwable: Throwable?) {}

}