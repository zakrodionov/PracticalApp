package com.zakrodionov.practicalapp.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.di.appModule
import com.zakrodionov.practicalapp.app.features.about.di.aboutModule
import com.zakrodionov.practicalapp.app.features.favorite.di.favoriteModule
import com.zakrodionov.practicalapp.app.features.login.di.loginModule
import com.zakrodionov.practicalapp.app.features.posts.di.postsModule
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
            androidLogger(Level.ERROR)
            modules(
                appModule,
                aboutModule,
                loginModule,
                favoriteModule,
                postsModule
            )
        }
    }

    private fun setupFlipper() {
        // FlipperInitializer.initFlipperPlugins(this) TODO wait fix
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
