package ir.miare.soccerino

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ir.miare.common.logger.Log
import ir.miare.common.logger.Logger
import ir.miare.soccerino.util.logger.TimberLogger
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    val logger: Logger = TimberLogger()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(tree = Timber.DebugTree())
            Log.init(logger = logger)
        }
    }

}