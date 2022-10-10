package com.example.todolist.db

import androidx.room.*
import com.example.todolist.ChatUser
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelDao {
    @Insert
    fun insertModel(info: Info)
    @Delete
    fun deleteModel(info: Info)
    @Update
    fun updateModel(info: Info)
    @Query("select * from Info")
    fun getAllModel(): Flow<List<Info>>


    @Insert
    fun insertChatUser(userInfo: ChatUserInfo)
    @Delete
    fun deleteChatUser(userInfo: ChatUserInfo)
    @Update
    fun updateChatUser(userInfo: ChatUserInfo)
    @Query("select * from ChatUserInfo")
    fun getAllChatUserInfo(): List<ChatUserInfo>
}