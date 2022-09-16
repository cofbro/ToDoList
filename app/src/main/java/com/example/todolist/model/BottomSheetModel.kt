package com.example.todolist.model

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetModel(application: Application) : AndroidViewModel(application) {

    val colorList = arrayListOf(
        "#89FB04", "#79B631", "#03A9F4", "#E6B00F", "#FA9706", "#FD1403"
    )
    private var todoString: String? = null
    private var chipID: Int? = null
    private var milliseconds: Long? = null

    fun saveTime(mMilliseconds: Long) {
        milliseconds = mMilliseconds
    }

    fun saveChipID(mChipID: Int) {
        chipID = mChipID
    }

    fun saveTodoStr(mTodoStr: String) {
        todoString = mTodoStr
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertToNormalTime(): String {
        if (milliseconds != null){
            return SimpleDateFormat("yyyyMMddhhmmss").format(Date(milliseconds!!))
        }
        return ""
    }

    fun getMilliseconds(): Int {
        if (milliseconds != null) {
            Log.d("chy","time:${System.currentTimeMillis() - milliseconds!!}")
            return -(System.currentTimeMillis() - milliseconds!!).toInt()
        }
        return 0
    }

    fun getTimeStr(): String {
        return convertToNormalTime()
    }

    fun getTodoStr(): String {
        return todoString!!
    }

    fun getChipStr(): String {
        return when (chipID) {
            0 -> {
                "#89FB04"
            }
            1 -> {
                "#79B631"
            }
            2 -> {
                "#03A9F4"
            }
            3 -> {
                "#E6B00F"
            }
            4 -> {
                "#FA9706"
            }
            5 -> {
                "#FD1403"
            }
            else -> {
                "0"
            }
        }
    }
}