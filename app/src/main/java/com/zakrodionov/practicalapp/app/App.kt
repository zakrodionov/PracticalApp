package com.zakrodionov.practicalapp.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.zakrodionov.common.extensions.isDebug
import com.zakrodionov.common.extensions.sdk26AndUp
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.FlipperInitializer
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()

        setupFlipper()
        setupTimber()

        initDefaultNotificationChannel()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(allModules)
            if (isDebug) {
                androidLogger(Level.ERROR)
            }
        }
    }

    private fun setupFlipper() {
        FlipperInitializer.initFlipperPlugins(this)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }

    private fun initDefaultNotificationChannel() {
        if (sdk26AndUp) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            )
        }
    }
}
