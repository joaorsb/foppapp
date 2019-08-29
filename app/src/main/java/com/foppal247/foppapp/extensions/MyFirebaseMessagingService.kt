package com.foppal247.foppapp.extensions

import android.content.Intent
import android.util.Log
import com.foppal247.foppapp.activity.MainActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FirebaseMessage"
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val intent = Intent(this, MainActivity::class.java)
        NotificationUtil.create(this, 1, intent,
            p0.notification?.title as String, p0.notification?.body as String )
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String){
        val tokenDB = FirebaseDatabase.getInstance().reference
        tokenDB.child("tokens").push().child("user_token").setValue(token)
    }

}