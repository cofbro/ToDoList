package com.example.todolist.lc

import android.app.Application
import cn.leancloud.LCLogger
import cn.leancloud.LeanCloud
import cn.leancloud.im.v2.LCIMMessageManager



class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LeanCloud.setLogLevel(LCLogger.Level.DEBUG)
        LeanCloud.initialize(
            this,
            "dOjVkqsXENI7Xva3Yw5jSS6n-gzGzoHsz",
            "6SrcdrMg8YBwLqia3AubooMc",
            "https://dojvkqsx.lc-cn-n1-shared.com"
        )
        LCIMMessageManager.setConversationEventHandler(CustomConversationEventHandler(applicationContext))
        LCIMMessageManager.registerDefaultMessageHandler(CustomMessageHandler(applicationContext))
    }
}
