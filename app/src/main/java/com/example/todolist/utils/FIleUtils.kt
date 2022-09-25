package com.example.todolist.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import cn.leancloud.LCFile
import com.example.todolist.model.MainViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.*


fun uploadImageToLC(context:Context, model: MainViewModel) {
    val name = "${model.userName}.jpg"
    val file = LCFile.withAbsoluteLocalPath(name, model.localAbsolutePath)
    file.saveInBackground().subscribe(object : Observer<LCFile?> {
        override fun onSubscribe(d: Disposable) { }

        override fun onNext(t: LCFile) {
            model.lcUri.postValue(t.url)

            Toast.makeText(context, "图片上传成功", Toast.LENGTH_SHORT).show()
        }

        override fun onError(e: Throwable) {
            Toast.makeText(context, "图片上传失败", Toast.LENGTH_SHORT).show()
        }

        override fun onComplete() {}
    })
}


fun getPathWithName(context: Context, name: String):String {
    return "${context.filesDir.path}/$name.jpg"
}

fun insertImage(context: Context,uri: Uri, name: String, callback: (String) -> Unit = {}) {
    context.contentResolver.openInputStream(uri).use {
        it?.let {
            val path = getPathWithName(context, name)
            writeToFile(it, path) {
                callback(path)
            }
        }
    }
}

private fun createDirectoryWithName(context: Context, name: String) {
    val path = getPathWithName(context, name)
    val file = File(path)
    if (!file.exists()) {
        file.mkdirs()
    }
}

private fun writeToFile(inputStream: InputStream, filePath: String, callback: () -> Unit) {
    BufferedInputStream(inputStream).use { bis ->
        BufferedOutputStream(FileOutputStream(filePath)).use { bos ->
            val buffer = ByteArray(1024)
            var len = bis.read(buffer, 0, 1024)
            while (len != -1) {
                bos.write(buffer, 0, len)
                len = bis.read(buffer, 0, 1024)
            }
            bos.flush()
        }
        callback()
    }
}