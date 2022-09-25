package com.example.todolist.model

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.leancloud.LCObject
import cn.leancloud.LCUser
import cn.leancloud.types.LCNull
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class MainViewModel : ViewModel() {
    var shouldShowNavigationView = MutableLiveData(false)
    val isPassword = MutableLiveData(true)
    val isChecked = MutableLiveData(true)
    val shouldShowAnimation = MutableLiveData(false)
    val verifyMethod = MutableLiveData(false)

    var isLogin = MutableLiveData(false)
    var userName = ""
    var psd = ""
    var motto = MutableLiveData("暂未设置座右铭")
    var welcomeStr = MutableLiveData("匿名用户")
    var lcUri = MutableLiveData("")
    var localAbsolutePath = ""
    var currentUser: LCUser? = null


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


    fun updateUserInfo(context: Context, user: LCUser) {
        user.saveInBackground().subscribe(object : Observer<LCObject> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: LCObject) {}

            override fun onError(e: Throwable) {}

            override fun onComplete() {}

        })
    }

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