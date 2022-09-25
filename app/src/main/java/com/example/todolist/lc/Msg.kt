package com.example.todolist.lc

class Msg(val message:String, val messageType: Int, val belong: String) {
    companion object {
        const val SEND = 0
        const val GET = 1
    }
}