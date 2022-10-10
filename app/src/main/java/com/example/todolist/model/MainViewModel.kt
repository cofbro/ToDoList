package com.example.todolist.model

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.leancloud.LCObject
import cn.leancloud.LCQuery
import cn.leancloud.LCUser
import cn.leancloud.types.LCNull
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author chy
 * 1.主要用于登录登出操作
 * 2.一些基本的UI显示
 */
class MainViewModel : ViewModel() {
    //是否显示导航栏
    var shouldShowNavigationView = MutableLiveData(false)

    //是否是密码登录
    val isPassword = MutableLiveData(true)

    //是否勾选checkBox
    val isChecked = MutableLiveData(true)

    //是否显示 提醒勾选checkBox 动画
    val shouldShowAnimation = MutableLiveData(false)

    //是否是手机号登录
    val verifyMethod = MutableLiveData(false)


    //是否已经登录
    var isLogin = MutableLiveData(false)

    //当前用户昵称
    var userName = ""

    //当前用户密码
    var psd = ""

    //当前用户座右铭
    var motto = MutableLiveData("暂未设置座右铭")

    //默认欢迎的姓名
    var welcomeStr = MutableLiveData("匿名用户")

    //lc后端云的图片url
    var lcUri = MutableLiveData("")

    ///文件本地绝对路径
    var localAbsolutePath = ""

    //当前用户
    var currentUser: LCUser? = null

    //以用户名和密码登录
    fun loginWithUserNameAndPassword(
        context: Context,
        userName: String,
        password: String,
        callback: (Boolean) -> Unit
    ) {
        LCUser.logIn(userName, password).subscribe(object : Observer<LCUser> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: LCUser) {
                currentUser = t
                val m = t.getString("motto")
                if (m != null) {
                    motto.postValue(m)
                }
                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                callback(true)
            }

            override fun onError(e: Throwable) {
                Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show()
                callback(false)
            }

            override fun onComplete() {}

        })
    }

    //注册
    fun signUpUser(context: Context, user: LCUser, callback: (Boolean) -> Unit) {
        user.signUpInBackground().subscribe(object : Observer<LCUser> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: LCUser) {
                callback(true)
                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Throwable) {
                callback(false)
                Toast.makeText(context, "用户名已存在", Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {}

        })
    }

    //登出
    fun quit(context: Context) {
        if (isLogin.value == true) {
            isLogin.postValue(false)
            motto.postValue("暂未设置座右铭")
            lcUri.postValue("")
            userName = "匿名用户"
            welcomeStr.postValue("匿名用户")
            Toast.makeText(context, "已退出", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "您还未登录", Toast.LENGTH_SHORT).show()
        }
    }

    //更新用户信息
    fun updateUserInfo(context: Context, user: LCUser) {
        user.saveInBackground().subscribe(object : Observer<LCObject> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: LCObject) {}

            override fun onError(e: Throwable) {}

            override fun onComplete() {}

        })
    }

    //修改密码
    fun changeThePassword(context: Context, user: LCUser) {
        LCUser.requestPasswordResetInBackground(user.email).subscribe(object : Observer<LCNull> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: LCNull) {
                Toast.makeText(context, "已向您的邮箱发送了一封邮件，请注意查收", Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Throwable) {
                Toast.makeText(context, "邮件发送失败", Toast.LENGTH_SHORT).show()
            }

            override fun onComplete() {}

        })
    }
}