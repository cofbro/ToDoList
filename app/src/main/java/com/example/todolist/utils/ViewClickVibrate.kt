package com.example.todolist.utils

import android.view.View

class ViewClickVibrate: View.OnClickListener {
    private val VIBRATE_TIME = 60
    override fun onClick(v: View) {
        VibrateHelp().vSimple(v.context, VIBRATE_TIME)
    }
}