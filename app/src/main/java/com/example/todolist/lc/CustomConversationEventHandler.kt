package com.example.todolist.lc

import android.util.Log
import cn.leancloud.LCFile
import cn.leancloud.LCQuery
import cn.leancloud.LCUser
import cn.leancloud.im.v2.LCIMClient
import cn.leancloud.im.v2.LCIMConversation
import cn.leancloud.im.v2.LCIMConversationEventHandler
import com.example.todolist.ChatUser
import com.example.todolist.model.ChatModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


// Java/Android SDK 通过定制自己的对话事件 Handler 处理服务端下发的对话事件通知
class CustomConversationEventHandler : LCIMConversationEventHandler() {
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
            val query = LCQuery<LCFile>("_File")
            query.whereEqualTo("name", "${operator}.jpg")
            query.findInBackground().subscribe(object : Observer<List<LCFile>> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: List<LCFile>) {
                    var pic = ""
                    pic = if (t.isEmpty()) "" else t[0].url
                    val model = ChatModel.getInstance()
                    val cu = ChatUser(operator, pic)
                    model.userList.add(cu)
                    model.userMutableList.postValue(model.userList)
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {}

            })
        }
    }
}