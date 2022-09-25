package com.example.todolist

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData

class PhoneEditText : AppCompatEditText {
    var phoneNumber = MutableLiveData("")
    private var onNumberChangeListener: ((String) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        addTextChangedListener(
            onTextChanged = { mText, _, before, count ->
                if (before < count) {
                    if (mText?.length == 3 || mText?.length == 8) {
                        text?.append(" ")
                    }
                }
            },
            afterTextChanged = {
                if (it?.length != 0) {
                    val number = it.toString().replace(" ", "")
                    phoneNumber.postValue(number)
                    onNumberChangeListener?.let { callback ->
                        callback(number)
                    }
                }
            }
        )
    }

    fun clear() {
        text?.clear()
        phoneNumber.postValue("")
    }
}