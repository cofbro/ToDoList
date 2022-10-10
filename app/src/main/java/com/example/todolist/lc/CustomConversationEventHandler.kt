package com.example.todolist.lc

import android.content.Context
import android.util.Log
import cn.leancloud.im.v2.LCIMClient
import cn.leancloud.im.v2.LCIMConversation
import cn.leancloud.im.v2.LCIMConversationEventHandler
import com.example.todolist.model.ChatModel


// Java/Android SDK 通过定制自己的对话事件 Handler 处理服务端下发的对话事件通知
class CustomConversationEventHandler(val context: Context) : LCIMConversationEventHandler() {
    override fun onMemberLeft(
        client: LCIMClient?,
        conversation: LCIMConversation?,
        members: MutableList<String>?,
        kickedBy: String?
    ) {
        Log.d("chy", "left")
    }

    override fun onMemberJoined(
        client: LCIMClient?,
        conversation: LCIMConversation?,
        members: MutableList<String>?,
        invitedBy: String?
    ) {
        Log.d("chy", "joined")
    }

    override fun onKicked(client: LCIMClient?, conversation: LCIMConversation?, kickedBy: String?) {
        Log.d("chy", "kicked")
    }

    override fun onInvited(
        client: LCIMClient?,
        conversation: LCIMConversation?,
        operator: String?
    ) {
        if (operator != null) {
            val model = ChatModel.getInstance()
            //查询该用户的头像等信息并保存
            model.saveUserAvatarAndUsernameIntoLocal(context, operator)
        }
    }
}