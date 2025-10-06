package ir.miare.common.logger

object Log : Logger {

    @Volatile
    private var delegate: Logger = NoOpLogger()

    fun init(logger: Logger) {
        delegate = logger
    }

    override fun v(tag: String?, message: String?, vararg args: Any?) {
        return delegate.v(tag = tag, message = message, args = args)
    }

    override fun v(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return delegate.v(tag = tag, throwable = throwable, message = message, args = args)
    }

    override fun v(tag: String?, throwable: Throwable?) {
        return delegate.v(tag = tag, throwable = throwable)
    }

    override fun d(tag: String?, message: String?, vararg args: Any?) {
        return delegate.d(tag = tag, message = message, args = args)
    }

    override fun d(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return delegate.d(tag = tag, throwable = throwable, message = message, args = args)
    }

    override fun d(tag: String?, throwable: Throwable?) {
        return delegate.d(tag = tag, throwable = throwable)
    }

    override fun i(tag: String?, message: String?, vararg args: Any?) {
        return delegate.i(tag = tag, message = message, args = args)
    }

    override fun i(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return delegate.i(tag = tag, throwable = throwable, message = message, args = args)
    }

    override fun i(tag: String?, throwable: Throwable?) {
        return delegate.i(tag = tag, throwable = throwable)
    }

    override fun w(tag: String?, message: String?, vararg args: Any?) {
        return delegate.w(tag = tag, message = message, args = args)
    }

    override fun w(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return delegate.w(tag = tag, throwable = throwable, message = message, args = args)
    }

    override fun w(tag: String?, throwable: Throwable?) {
        return delegate.w(tag = tag, throwable = throwable)
    }

    override fun e(tag: String?, message: String?, vararg args: Any?) {
        return delegate.e(tag = tag, message = message, args = args)
    }

    override fun e(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return delegate.e(tag = tag, throwable = throwable, message = message, args = args)
    }

    override fun e(tag: String?, throwable: Throwable?) {
        return delegate.e(tag = tag, throwable = throwable)
    }

}