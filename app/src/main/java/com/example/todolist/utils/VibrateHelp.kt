package com.example.todolist.utils

import android.content.Context
import android.os.Vibrator




class VibrateHelp {
    private var vibrator: Vibrator? = null

    /**
     * @ClassName:VibrateHelp - 简单的震动
     * @author:CaoJiaHao
     * @Param:context 调用震动类的 context
     * @param:millisecond 震动的时间
     */
    fun vSimple(context: Context, millisecode: Int) {
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        vibrator!!.vibrate(millisecode.toLong())
    }

    /**
     * @param : pattern 震动的形式
     * @param : repeate 震动循环的次数
     * @ClassName:VibrateHelp - 复杂的震动
     * @author:CaoJiaHao
     * @Param: context 调用复杂震动的context
     */
    fun vComplicated(context: Context, pattern: LongArray?, repeate: Int) {
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        vibrator!!.vibrate(pattern, repeate)
    }

    /**
     * @ClassName:VibrateHelp - 停止震动
     * @author:CaoJiaHao
     */
    fun stop() {
        if (vibrator != null) vibrator!!.cancel()
    }
}