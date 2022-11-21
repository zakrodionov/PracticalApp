package com.zakrodionov.practicalapp.app.features.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.moshi.Moshi
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.common.extensions.fromJson
import com.zakrodionov.common.extensions.tryOrReturnDefault
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.root.DeepLinkNavigation
import com.zakrodionov.practicalapp.app.features.root.RootActivity

class FirebaseMsgService : FirebaseMessagingService() {

    private val moshi = Moshi.Builder().build()

    companion object {
        const val ARG_PUSH_DATA = "ARG_PUSH_DATA"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        debug("AppFirebaseMessagingService - onMessageReceived - $remoteMessage")

        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]
        val deepLinks = remoteMessage.data["deepLinkNavigation"]

        val deepLinkNavigation = tryOrReturnDefault(
            block = { deepLinks?.fromJson(moshi) },
            default = { DeepLinkNavigation.empty }
        )

        val intent = Intent(this, RootActivity::class.java).apply {
            putExtra(ARG_PUSH_DATA, deepLinkNavigation)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_pets)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        debug("AppFirebaseMessagingService - onNewToken - $token")
    }
}
