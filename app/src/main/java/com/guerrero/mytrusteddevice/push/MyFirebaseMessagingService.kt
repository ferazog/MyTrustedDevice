package com.guerrero.mytrusteddevice.push

import android.app.NotificationManager
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.guerrero.mytrusteddevice.R
import com.guerrero.mytrusteddevice.factorverifier.FactorVerifier
import com.guerrero.mytrusteddevice.shared.PushContent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var factorVerifier: FactorVerifier

    override fun onNewToken(token: String) {
        factorVerifier.updatePushToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        when (remoteMessage.data["type"]) {
            "verify_push_challenge" -> {
                val factorSid = remoteMessage.data["factor_sid"]
                val challengeSid = remoteMessage.data["challenge_sid"]
                val message = remoteMessage.data["message"]
                if (factorSid != null && challengeSid != null) {
                    val pushContent = PushContent(
                        challengeSid,
                        factorSid,
                        applicationContext.getString(R.string.new_challenge),
                        message.toString()
                    )
                    sendNotification(pushContent)
                }
            }
            else -> {

            }
        }
    }

    private fun sendNotification(pushContent: PushContent) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(pushContent, applicationContext)
    }
}
