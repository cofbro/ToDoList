package com.example.todolist.events

import androidx.databinding.BindingAdapter
import com.example.todolist.utils.hideTheBottomNavigationView
import com.example.todolist.utils.showTheBottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView

@BindingAdapter("showOrHide")
fun BottomNavigationView.showOrHide(post: Boolean) {
    if (post) {
        showTheBottomNavigationView(this)
    } else {
        hideTheBottomNavigationView(this)
    }
}

