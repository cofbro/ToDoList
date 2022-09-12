package com.example.todolist.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Matters(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val number: Int
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
    val minute: String
)