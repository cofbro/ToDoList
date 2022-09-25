package com.example.todolist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ChatUser(
    val username: String,
    val avatarUrl: String
): Parcelable