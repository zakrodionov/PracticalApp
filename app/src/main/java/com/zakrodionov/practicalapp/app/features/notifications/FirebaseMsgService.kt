package com.zakrodionov.practicalapp.app.features.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.common.extensions.getStringField
import com.zakrodionov.common.extensions.safelyParseJson
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.root.RootActivity

class FirebaseMsgService : FirebaseMessagingService() {

    companion object {
        const val ARG_PUSH_DATA = "ARG_PUSH_DATA"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        debug("AppFirebaseMessagingService - onMessageReceived - $remoteMessage")

        val data = remoteMessage.data["data"].safelyParseJson()

        val title = data.getStringField("title")
        val message = data.getStringField("text")
        val screen = data.getStringField("screen")

        val intent = Intent(this, RootActivity::class.java).apply {
            putExtra(ARG_PUSH_DATA, screen)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
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
