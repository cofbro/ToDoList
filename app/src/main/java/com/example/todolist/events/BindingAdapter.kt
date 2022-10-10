package com.example.todolist.events

import android.animation.ObjectAnimator
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.databinding.BindingAdapter
import com.example.todolist.PhoneEditText
import com.example.todolist.model.MainViewModel
import com.example.todolist.utils.dp2px
import com.example.todolist.utils.hideTheBottomNavigationView
import com.example.todolist.utils.showTheBottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView
//底部导航栏的显示与隐藏
@BindingAdapter("showOrHide")
fun BottomNavigationView.showOrHide(post: Boolean) {
    if (post) {
        showTheBottomNavigationView(this)
    } else {
        hideTheBottomNavigationView(this)
    }
}
//设置手机号后面 x 图片的显示与隐藏
@BindingAdapter("setImageVisibility")
fun ImageView.setImageVisibility(phoneNumber: String) {
    Log.d("chy","number:$phoneNumber")
    visibility = if (phoneNumber.length == 11) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}
//设置输入框的输入格式
@BindingAdapter("changePasswordShow")
fun EditText.changePasswordShow(isPassword: Boolean) {
    transformationMethod = if (isPassword) {
        PasswordTransformationMethod.getInstance()
    }else {
        HideReturnsTransformationMethod.getInstance()
    }
}
//底部确认按钮是否可点
@BindingAdapter("setEnabled")
fun Button.setEnabled(phoneNumber: String) {
    isEnabled = phoneNumber.length == 11
}
//是否显示 checkbox 未勾选的动画
@BindingAdapter("shouldShowAnimation")
fun CheckBox.shouldShowAnimation(isShow: Boolean) {
    val offsetX = context.dp2px(3).toFloat()
    if (isShow) {
        val animator = ObjectAnimator.ofFloat(
            this,
            "translationX",
            0f,-offsetX,offsetX * 2,0f
        )
        animator.duration = 100
        animator.repeatCount = 2
        animator.interpolator = LinearInterpolator()
        animator.start()
    }
}

@BindingAdapter("setVisibility")
fun View.setImageVisibility(isShow: Boolean) {
    visibility = if (isShow) {
        View.VISIBLE
    }else {
        View.GONE
    }
}

@BindingAdapter("setPhoneEditTextVisibility")
fun View.setPhoneEditTextVisibility(isShow: Boolean) {
    visibility = if (isShow) {
        View.GONE
    }else {
        View.VISIBLE
    }
}

@BindingAdapter("changeTitle")
fun Button.changeTitle(shouldChange: Boolean) {
    text = if (shouldChange) {
        "点击登录"
    } else {
        "点击获取验证码"
    }

}