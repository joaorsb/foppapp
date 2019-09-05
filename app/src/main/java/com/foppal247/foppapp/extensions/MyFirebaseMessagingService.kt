package com.foppal247.foppapp.extensions

import android.content.Intent
import android.util.Log
import com.foppal247.foppapp.FoppalApplication
import com.foppal247.foppapp.activity.MainActivity
import com.foppal247.foppapp.activity.NewsListActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.jetbrains.anko.support.v4.startActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FirebaseMessage"
    override fun onMessageReceived(messageReceived: RemoteMessage) {
        super.onMessageReceived(messageReceived)
        lateinit var intent: Intent
        if(messageReceived.data.isNotEmpty()) {
            val data = messageReceived.data
            FoppalApplication.getInstance().country = data.get("country") as String
            FoppalApplication.getInstance().selectedIntlTeamName = data["intlName"] as String
            FoppalApplication.getInstance().englishNews = false
            FoppalApplication.getInstance().newsList = mutableListOf()
            FoppalApplication.getInstance().selectedTeamName = data["intlName"] as String
            intent = Intent(this, NewsListActivity::class.java)

        } else {
            intent = Intent(this, MainActivity::class.java)
        }

        NotificationUtil.create(this, 1, intent,
            messageReceived.notification?.title as String, messageReceived.notification?.body as String )
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