package com.example.todolist.model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.leancloud.LCFile
import cn.leancloud.LCObject
import cn.leancloud.LCQuery
import cn.leancloud.LCUser
import cn.leancloud.im.v2.LCIMClient
import cn.leancloud.im.v2.LCIMConversation
import cn.leancloud.im.v2.LCIMMessage
import com.example.todolist.ChatUser
import com.example.todolist.db.ChatUserInfo
import com.example.todolist.db.Repository
import com.example.todolist.lc.GetTextItem
import com.example.todolist.lc.Msg
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChatModel : ViewModel() {

    //单例
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

    //将用户发送的消息储存起来
    var receiveMessageList = arrayListOf<Msg>()
    //上同（post）
    var receiveMessageMutableList = MutableLiveData<List<Msg>>()
    //对话端
    var client: LCIMClient? = null
    //默认列表用户（客服）
    var userList = arrayListOf(
        ChatUser(
            "小c的bro",
            "http://lc-dOjVkqsX.cn-n1.lcfile.com/lubKftkSxUJLP18FxXkqSOtTQw8pFr4h/%E5%B0%8Fc%E7%9A%84bro.jpg"
        )
    )
    //用户列表用户信息实时更新（post）
    var userMutableList = MutableLiveData<List<ChatUser>>()
    //是否加载过消息记录
    var ifLoadRecords = false

    fun saveReceiveMessage(msg: Msg) {
        receiveMessageList.add(msg)
    }

    //将符合条件的对话消息过滤出来，显示到UI上
    fun msgListFilter(msgList: List<Msg>, operator: String, username: String): List<Msg> {
        return msgList.filter {
            it.belong.contains("${operator}&${username}") || it.belong.contains("${username}&${operator}")
        }
    }

    //将查询出来的历史消息存进chatModel
    fun saveIntoModel(
        msgList: MutableList<LCIMMessage>,
        mainViewModel: MainViewModel,
        conversation: LCIMConversation
    ) {
        if (!ifLoadRecords) {
            msgList.forEach {
                val message = Gson().fromJson(it.content, GetTextItem::class.java)
                if (mainViewModel.currentUser!!.username == it.from) {
                    //以send方式存储消息
                    val msg = Msg(message.lctext, Msg.SEND, conversation.name)
                    receiveMessageList.add(msg)
                } else {
                    //以get方式存储消息
                    val msg = Msg(message.lctext, Msg.GET, conversation.name)
                    receiveMessageList.add(msg)
                }
            }
            receiveMessageMutableList.postValue(receiveMessageList)
            ifLoadRecords = true
        }
    }


    fun saveUserAvatarAndUsernameIntoLocal(context: Context, username: String) {
        //查询该用户的头像
        val query = LCQuery<LCFile>("_File")
        query.whereEqualTo("name", "${username}.jpg")
        query.findInBackground().subscribe(object : Observer<List<LCFile>> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: List<LCFile>) {
                //获取改用户的头像url
                val pic: String = if (t.isEmpty()) "" else t[0].url
                val cu = ChatUser(username, pic)
                //判断列表中是否有这个用户
                if (!userList.contains(cu)) {
                    userList.add(cu)
                    userMutableList.postValue(userList)
                    val repository = Repository(context)
                    //将该用户信息写入手机储存
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.insertUserInfo(ChatUserInfo(0, username, pic, ""))
                    }
                }
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}

        })
    }

    //搜索用户
    fun searchUserByUsername(context: Context, userName: String, response: (Boolean) -> Unit) {
        val query = LCQuery<LCObject>("_User")
        query.whereEqualTo("username", userName)
        query.findInBackground().subscribe(object : Observer<List<LCObject>> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: List<LCObject>) {
                if (t.isEmpty()) {
                    response(false)
                    Toast.makeText(context, "没有此用户", Toast.LENGTH_SHORT).show()
                } else {
                    response(true)
                    val user = t[0] as LCUser
                    saveUserAvatarAndUsernameIntoLocal(context, user.username)
                    Toast.makeText(context, "消息已发出，等待对方确认", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}

        })
    }
}