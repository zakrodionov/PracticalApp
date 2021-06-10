package com.zakrodionov.practicalapp.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.FlipperInitializer
import com.zakrodionov.practicalapp.R
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupFlipper()
        setupTimber()

        initDefaultNotificationChannel()
    }

    private fun setupFlipper() {
        FlipperInitializer.initFlipperPlugins(this)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }

    private fun initDefaultNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            )
        }
    }
}
