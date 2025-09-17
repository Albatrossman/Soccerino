package ir.miare.soccerino.util.logger

import ir.miare.common.logger.Logger
import timber.log.Timber

class TimberLogger : Logger {

    override fun v(tag: String?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).v(message = message, args = args)
    }

    override fun v(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).v(t = throwable, message = message, args = args)
    }

    override fun v(tag: String?, throwable: Throwable?) {
        return Timber.tagged(tag = tag).v(t = throwable)
    }

    override fun d(tag: String?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).d(message = message, args = args)
    }

    override fun d(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).d(t = throwable, message = message, args = args)
    }

    override fun d(tag: String?, throwable: Throwable?) {
        return Timber.tagged(tag = tag).d(t = throwable)
    }

    override fun i(tag: String?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).i(message = message, args = args)
    }

    override fun i(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).i(t = throwable, message = message, args = args)
    }

    override fun i(tag: String?, throwable: Throwable?) {
        return Timber.tagged(tag = tag).i(t = throwable)
    }

    override fun w(tag: String?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).w(message = message, args = args)
    }

    override fun w(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).w(t = throwable, message = message, args = args)
    }

    override fun w(tag: String?, throwable: Throwable?) {
        return Timber.tagged(tag = tag).w(t = throwable)
    }

    override fun e(tag: String?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).e(message = message, args = args)
    }

    override fun e(tag: String?, throwable: Throwable?, message: String?, vararg args: Any?) {
        return Timber.tagged(tag = tag).e(t = throwable, message = message, args = args)
    }

    override fun e(tag: String?, throwable: Throwable?) {
        return Timber.tagged(tag = tag).e(t = throwable)
    }

    private fun Timber.Forest.tagged(tag: String?): Timber.Tree {
        return if (tag == null) Timber else Timber.tag(tag = tag)
    }

}