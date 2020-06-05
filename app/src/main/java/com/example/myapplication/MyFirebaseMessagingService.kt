package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompatSideChannelService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e("ljy", "token $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.notification !=  null) {
            Log.e("ljy", "알림 메시지 " + remoteMessage.notification?.body)

            var messageBody = remoteMessage.notification?.body
            var messageTitle = remoteMessage.notification?.title

            var intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            var channelId = "Channel ID"

            var defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            var notificationBuilder =
                NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)

            var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                var chnnelName = "Channel Name"
                var channel = NotificationChannel(channelId, chnnelName, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(0, notificationBuilder.build())
        }
    }
}