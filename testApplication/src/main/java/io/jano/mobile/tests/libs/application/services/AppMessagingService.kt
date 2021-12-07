package io.jano.mobile.tests.libs.application.services

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.jano.mobile.libs.android.JanoSDK

class AppMessagingService : FirebaseMessagingService() {

    private val userId = "test-user"
    private val deviceId = "test-emulator"

    override fun onMessageReceived(remoteNotification: RemoteMessage) {
        super.onMessageReceived(remoteNotification)
    }

    override fun handleIntent(intent: Intent) {
        val decryptedIntent = JanoSDK.decryptNotification(userId, deviceId, intent = intent)
        super.handleIntent(decryptedIntent ?: intent)
    }
}