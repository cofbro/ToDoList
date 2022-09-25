package com.example.todolist.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.MainActivity
import com.example.todolist.NotificationReceiver
import com.example.todolist.R
import kotlinx.coroutines.*


fun Context.dp2px(dp: Int): Int {
    return (resources.displayMetrics.density * dp).toInt()
}

fun startRepeatingJob(timeInterval: Long, recyclerView: RecyclerView, doSomething: () -> Unit = {}): Job {
    var flag = true
    var tag = 0
    val job = CoroutineScope(Dispatchers.Default).launch {
        while (flag) {
            flag = false
            delay(timeInterval)
            withContext(Dispatchers.Main) {
                if (tag % 2 == 0) {
                    recyclerView.smoothScrollBy(0, -recyclerView.context.dp2px(25), LinearInterpolator(), 0)
                } else {
                    recyclerView.smoothScrollBy(0, recyclerView.context.dp2px(25), LinearInterpolator(), 800)
                }
                flag = true
                tag++
            }
        }
    }
    return job
}

fun postDelay(duration: Long, callback: () -> Unit = {}) {
    CoroutineScope(Dispatchers.IO).launch {
        delay(duration)
        withContext(Dispatchers.Main) {
            callback()
        }
    }
}

fun setAlarmToNotification(
    context: Context,
    time: Int,
    isSetAlarm: Boolean,
    content: String,
    timeStr: String
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    intent.putExtra("content", content)
    intent.putExtra("timeStr", timeStr)
    intent.action = "com.example.todolist.receiver"
    //更新数据
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        System.currentTimeMillis().toInt(),
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
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
