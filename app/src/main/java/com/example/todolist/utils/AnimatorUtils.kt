package com.example.todolist.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.*
import com.example.todolist.R
import com.example.todolist.TodoListView
import com.google.android.material.bottomnavigation.BottomNavigationView

fun showTheBottomNavigationView(bottomNavigationView: BottomNavigationView) {
    ObjectAnimator.ofFloat(bottomNavigationView,"translationY",0f).apply {
        duration = 0
    }.start()
}

fun hideTheBottomNavigationView(bottomNavigationView: BottomNavigationView) {
    ObjectAnimator.ofFloat(bottomNavigationView,"translationY",300f).apply {
        duration = 0
    }.start()
}

fun shakeDenoteDeleting(context: Context, view: View) {

    ObjectAnimator.ofFloat(view, "translationX",0f,-20f,0f,20f,0f).apply {
        duration = 200
        repeatMode = ObjectAnimator.REVERSE
        repeatCount = 2
        interpolator = LinearInterpolator()
    }.start()
}

fun helperAnimator(view: View) {
    ObjectAnimator.ofFloat(view, "translationY",600f,0f).apply {
        duration = 600
        interpolator = BounceInterpolator()
        addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

        })
    }.start()

    val animation = AlphaAnimation(0f,1f).apply {
        duration = 800
        fillAfter = true
        interpolator = LinearInterpolator()
    }
    view.startAnimation(animation)


}