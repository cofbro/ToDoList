package com.example.todolist.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.leancloud.im.v2.LCIMClient
import com.example.todolist.ChatUser
import com.example.todolist.lc.Msg


class ChatModel : ViewModel() {

    companion object {
        private var INSTANCE: ChatModel? = null
        fun getInstance(): ChatModel {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = ChatModel()
                }
            }
            return INSTANCE!!
        }
    }

    var receiveMessageList = arrayListOf<Msg>()
    var receiveMessageMutableList = MutableLiveData<List<Msg>>()
    fun saveReceiveMessage(msg: Msg) {
        receiveMessageList.add(msg)
    }

    var userList = arrayListOf(
        ChatUser(
            "小c的bro",
            "http://lc-dOjVkqsX.cn-n1.lcfile.com/lubKftkSxUJLP18FxXkqSOtTQw8pFr4h/%E5%B0%8Fc%E7%9A%84bro.jpg"
        )
    )
    var userMutableList = MutableLiveData<List<ChatUser>>()

    fun msgListFilter(msgList: List<Msg>, operator: String, username: String): List<Msg> {
        return msgList.filter {
            it.belong.contains("${operator}&${username}") || it.belong.contains("${username}&${operator}")
        }
    }

     var client: LCIMClient? = null
}