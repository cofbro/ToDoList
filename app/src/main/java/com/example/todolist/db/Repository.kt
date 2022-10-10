package com.example.todolist.db

import android.content.Context
import kotlinx.coroutines.flow.Flow

class Repository(context: Context) {
    private var modelDao: ModelDao

    init {
        modelDao = ModelDataBase.getInstance(context).modelDao()
    }

    fun insertModel(info: Info) {
        modelDao.insertModel(info)
    }

    fun deleteModel(info: Info) {
        modelDao.deleteModel(info)
    }

    fun updateModel(info: Info) {
        modelDao.updateModel(info)
    }

    fun getAllModels(): Flow<List<Info>> {
        return modelDao.getAllModel()
    }

    fun insertUserInfo(userInfo: ChatUserInfo) {
        modelDao.insertChatUser(userInfo)
    }

    fun deleteUserInfo(userInfo: ChatUserInfo) {
        modelDao.deleteChatUser(userInfo)
    }

    fun updateUserInfo(userInfo: ChatUserInfo) {
        modelDao.updateChatUser(userInfo)
    }

    fun getAllUserInfo(): List<ChatUserInfo> {
        return modelDao.getAllChatUserInfo()
    }
}