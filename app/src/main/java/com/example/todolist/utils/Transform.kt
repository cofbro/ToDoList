package com.example.todolist.utils

import android.content.Context


fun Context.dp2px(dp: Int): Int {
    return (resources.displayMetrics.density * dp).toInt()
}