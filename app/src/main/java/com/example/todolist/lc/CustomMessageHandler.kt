package com.example.todolist.lc

import android.content.Context
import android.util.Log
import android.widget.Toast
import cn.leancloud.im.v2.LCIMClient
import cn.leancloud.im.v2.LCIMConversation
import cn.leancloud.im.v2.LCIMMessage
import cn.leancloud.im.v2.LCIMMessageHandler
import cn.leancloud.im.v2.messages.LCIMTextMessage
import com.example.todolist.model.ChatModel

class CustomMessageHandler(val context: Context) : LCIMMessageHandler() {

    override fun onMessage(
        message: LCIMMessage?,
        conversation: LCIMConversation?,
        client: LCIMClient?
    ) {
        //接收消息
        super.onMessage(message, conversation, client)
        if (message is LCIMTextMessage) {
            val model = ChatModel.getInstance()
            val msg = Msg(message.text, Msg.GET, conversation!!.name)
            model.saveReceiveMessage(msg)
            model.receiveMessageMutableList.postValue(model.receiveMessageList)
            Log.d("chy", "接收成功：${message.text}")
        }
    }
}