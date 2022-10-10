package com.example.todolist.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class ChatUserInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var imageUrl: String,
    var lastMsg: String
) : Parcelable

@Entity
data class Info(
    @PrimaryKey(autoGenerate = true)
    val modelId: Int,
    val todoStr: String,
    val color: String,
    val day: String,
    val month: String,
    val hour: String,
    val minute: String,
    var leftNum: Int,
    var rightNum: Int,
    val millisecond: Int
)