package com.example.todolist

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat



class NotificationReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent != null) {
            if (intent.action!! == "com.example.todolist.receiver") {
                val content = intent.getStringExtra("content")
                val timeStr = intent.getStringExtra("timeStr")
                val manager =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val pi = PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
                val notificationChannel =
                    NotificationChannel("status1", "状态", NotificationManager.IMPORTANCE_HIGH)
                manager.createNotificationChannel(notificationChannel)

                val builder = NotificationCompat.Builder(context, "2222")
                    .setSmallIcon(R.drawable.icon)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle("任务待办通知")
                    .setContentIntent(pi)
                    .setFullScreenIntent(pi, true)
                    .setPriority(NotificationManager.IMPORTANCE_MAX)
                    .setChannelId("status1")
                    .setContentText("您还未完成 $content 待办事项，$timeStr 截止哦！")
                manager.notify(System.currentTimeMillis().toInt(), builder.build())
            }
        }
    }

}
