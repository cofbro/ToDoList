package com.example.todolist.lc

import android.content.Context
import android.util.Log
import android.widget.Toast
import cn.leancloud.LCUser
import cn.leancloud.im.v2.*
import cn.leancloud.im.v2.callback.LCIMClientCallback
import cn.leancloud.im.v2.callback.LCIMConversationCallback
import cn.leancloud.im.v2.callback.LCIMConversationCreatedCallback
import cn.leancloud.im.v2.callback.LCIMMessagesQueryCallback
import cn.leancloud.im.v2.messages.LCIMTextMessage
import com.example.todolist.model.ChatModel
import com.example.todolist.model.MainViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


fun loginChatServer(chatModel: ChatModel, username: String, callback: (LCIMClient?) -> Unit = {}) {
    if (chatModel.client == null) {
        val user = LCIMClient.getInstance(username)
        user.open(object : LCIMClientCallback() {
            override fun done(client: LCIMClient?, e: LCIMException?) {
                if (e == null) {
                    Log.d("chy","登陆成功")
                    callback(user)
                }
            }

        })
    }
}


fun loginChatServer(
    context: Context,
    model: MainViewModel,
    callback: (LCIMClient?) -> Unit = {}
) {

    if (model.userName != "" && model.psd != "") {
        LCUser.logIn(model.userName, model.psd)
            .subscribe(object : Observer<LCUser> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: LCUser) {
                    val client = LCIMClient.getInstance(t)
                    Log.d("chy", "${t.username}登录成功")
                    client.open(object : LCIMClientCallback() {
                        override fun done(client: LCIMClient?, e: LCIMException?) {
                            if (e == null) {
                                Log.d("chy", "服务器链接成功")
                                callback(client)
                            }
                        }

                    })
                }

                override fun onError(e: Throwable) {
                    e.message?.let { Log.d("chy", it) }
                }

                override fun onComplete() {}

            })
    }


}

fun createConversationWithUser(
    client: LCIMClient,
    model: MainViewModel,
    toUsername: String,
    callback: (LCIMConversation?) -> Unit
) {
    if (toUsername != model.userName) {
        client.createConversation(
            listOf(toUsername),
            "${model.currentUser?.username}&$toUsername",
            null,
            false,
            true,
            object : LCIMConversationCreatedCallback() {
                override fun done(
                    conversation: LCIMConversation?,
                    e: LCIMException?
                ) {
                    Log.d("chy", "创建对话成功")
                    callback(conversation)
                }

            })
    }
}

fun sendMegToServer(text: String, conversation: LCIMConversation?) {
    val msg = LCIMTextMessage()
    msg.text = text
    // 发送消息
    conversation?.sendMessage(msg, object : LCIMConversationCallback() {
        override fun done(e: LCIMException?) {
            if (e == null) {
                Log.d("chy", "发送成功！")
            } else {
                Log.d("chy","error:${e.message}")
            }
        }
    })
}

fun queryMessageRecords(conversation: LCIMConversation?, callback: (MutableList<LCIMMessage>?) -> Unit) {
    conversation?.queryMessages(10, object : LCIMMessagesQueryCallback() {
        override fun done(messages: MutableList<LCIMMessage>?, e: LCIMException?) {
            callback(messages)
        }

    })
}