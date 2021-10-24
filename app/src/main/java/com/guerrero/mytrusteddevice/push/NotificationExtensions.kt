package com.guerrero.mytrusteddevice.push

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.guerrero.mytrusteddevice.R
import com.guerrero.mytrusteddevice.shared.PushContent
import com.guerrero.mytrusteddevice.view.details.ChallengeDetailsActivity
import com.guerrero.mytrusteddevice.view.details.KEY_CHALLENGE_ID
import com.guerrero.mytrusteddevice.view.details.KEY_FACTOR_SID

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
    pushContent: PushContent,
    applicationContext: Context
) {
    val challengeDetailsIntent = Intent(
        applicationContext, ChallengeDetailsActivity::class.java
    ).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra(KEY_CHALLENGE_ID, pushContent.challengeId)
        putExtra(KEY_FACTOR_SID, pushContent.factorSid)
    }
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        challengeDetailsIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val channelId = applicationContext.getString(R.string.notification_channel_id)
    val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId).apply {
        setSmallIcon(R.drawable.ic_verified_user)
        setContentTitle(pushContent.title)
        setContentText(pushContent.message)
        //setContentIntent(pendingIntent)
        setAutoCancel(true)
        addAction(
            R.drawable.ic_verified_user,
            applicationContext.getString(R.string.open),
            pendingIntent
        )
    }
    notify(NOTIFICATION_ID, notificationBuilder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
