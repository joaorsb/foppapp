package com.foppal247.foppapp.extensions

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.foppal247.foppapp.R

object NotificationUtil {
    fun create(context: Context, id: Int, intent: Intent, contentTitle: String,
               contetText: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val p = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context, "id")
            .setContentIntent(p)
            .setContentTitle(contentTitle)
            .setContentText(contetText)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)

        val n = builder.build()
        manager.notify(id, n)
    }
}