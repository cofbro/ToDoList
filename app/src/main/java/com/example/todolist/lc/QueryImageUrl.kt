package com.example.todolist.lc

import cn.leancloud.LCFile
import cn.leancloud.LCQuery
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class QueryImageUrl {
    fun queryImageFromLC(userName: String, callback: (String) -> Unit) {
        val query = LCQuery<LCFile>("_File")
        query.whereEqualTo("name", "$userName.jpg")
        query.findInBackground().subscribe(object : Observer<List<LCFile>> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: List<LCFile>) {
                if (t.isNotEmpty()) {
                    callback(t[0].url)
                }
            }

            override fun onError(e: Throwable) {
                callback("false")
            }

            override fun onComplete() {}
        })
    }
}