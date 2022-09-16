package com.example.todolist.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.todolist.MainActivity
import com.example.todolist.NotificationReceiver
import com.example.todolist.R


fun Context.dp2px(dp: Int): Int {
    return (resources.displayMetrics.density * dp).toInt()
}

fun setAlarmToNotification(context: Context, time: Int, isSetAlarm: Boolean, content: String, timeStr: String) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    intent.putExtra("content", content)
    intent.putExtra("timeStr", timeStr)
    intent.action = "com.example.todolist.receiver"
    //更新数据
    val pendingIntent = PendingIntent.getBroadcast(context, System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
    if (isSetAlarm) {
        val triggerAtTime = SystemClock.elapsedRealtime() + time; //获取手机开机到现在的时间 加上你要延时的时间
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerAtTime,
            pendingIntent
        )//未设提醒，设置提醒
    } else {
        alarmManager.cancel(pendingIntent);//已设提醒，取消之前提醒，重新设置
    }
}
