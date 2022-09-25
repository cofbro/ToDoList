package com.example.todolist.events

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import cn.leancloud.LCQuery
import cn.leancloud.LCUser
import com.example.todolist.PhoneEditText
import com.example.todolist.R
import com.example.todolist.databinding.FragmentLoginBinding
import com.example.todolist.model.MainViewModel
import com.example.todolist.utils.saveUser
import com.example.todolist.utils.saveUserName
import com.example.todolist.utils.saveUserPsd
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*

class ClickEvents {

    fun clear(view: View, phoneEditText: PhoneEditText) {
        phoneEditText.clear()
    }

    fun changeEye(view: View, model: MainViewModel) {
        val v = view as ImageView
        if (model.isPassword.value == false) {
            v.setImageResource(R.drawable.eye)
            model.isPassword.postValue(true)
        } else {
            v.setImageResource(R.drawable.eyes_close)
            model.isPassword.postValue(false)
        }
    }

    fun changeCheckedStatus(view: View, model: MainViewModel) {
        val v = view as CheckBox
        model.isChecked.value = model.isChecked.value == false

    }

    fun showAnimation(
        view: View,
        model: MainViewModel,
        phoneEditText: PhoneEditText,
        binding: FragmentLoginBinding
    ) {
        if (model.isChecked.value == true) {
            model.shouldShowAnimation.postValue(true)

        } else {
            //手机号
            if (model.verifyMethod.value == false) {
                //默认手机号和用户名一样
                val name = phoneEditText.phoneNumber.value!!

                Toast.makeText(view.context, "暂不支持手机号登录", Toast.LENGTH_SHORT).show()
//                    if (!it) {
//                        //手机号注册
//                        Toast.makeText(view.context, "手机号注册成功", Toast.LENGTH_SHORT).show()
//                    } else {
//                        //手机号登录
//                        Toast.makeText(view.context, "手机号登录成功", Toast.LENGTH_SHORT).show()
//                    }

            } else {
                //用户名加密码
                val name = binding.userNameView.text.toString()
                val psd = binding.passwordEditText.text.toString()
                if (name != "" && psd != "") {
                    //登录
                    model.loginWithUserNameAndPassword(view.context, name, psd) { b ->
                        if (b) {
                            model.viewModelScope.launch(Dispatchers.IO) {
                                delay(200)
                                withContext(Dispatchers.Main) {
                                    model.isLogin.postValue(true)
                                    model.welcomeStr.postValue(name)
                                    model.userName = name
                                    model.psd = psd
                                    saveUser(view.context, true)
                                    saveUserName(view.context, name)
                                    saveUserPsd(view.context, psd)
                                    binding.root.findNavController().navigateUp()
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(view.context, "请输入完整的用户名和密码", Toast.LENGTH_SHORT).show()
                }
            }
            model.shouldShowAnimation.postValue(false)
        }
    }

    fun chengVerifyMethod(view: View, model: MainViewModel, binding: FragmentLoginBinding) {
        val v = view as TextView
        if (model.verifyMethod.value == true) {
            model.verifyMethod.postValue(false)
            binding.button.isEnabled = false
            v.text = "密码登录"
        } else {
            model.verifyMethod.postValue(true)
            binding.button.isEnabled = true
            v.text = "验证码登录"

        }
    }

    fun register(view: View) {
        view.findNavController()
    }

    fun turnToRegisterPage(view: View) {
        view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun queryUser(userName: String, callback: (Boolean) -> Unit) {
        val query = LCQuery<LCUser>("_User")
        query.whereEqualTo("username", userName)
        query.findInBackground().subscribe(object : Observer<List<LCUser>> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: List<LCUser>) {
                if (t.isNotEmpty())
                    callback(true)
                else callback(false)
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}

        })
    }


}