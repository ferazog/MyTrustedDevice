package com.guerrero.mytrusteddevice.push

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.guerrero.mytrusteddevice.R
import com.guerrero.mytrusteddevice.view.MainActivity

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
    title: String,
    message: String,
    applicationContext: Context
) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val channelId = applicationContext.getString(R.string.notification_channel_id)
    val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
        .setSmallIcon(R.drawable.ic_verified_user)
        .setContentTitle(title)
        .setContentText(message)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
    notify(NOTIFICATION_ID, notificationBuilder.build())
}
