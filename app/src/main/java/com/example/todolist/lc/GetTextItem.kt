package com.example.todolist.lc


import com.google.gson.annotations.SerializedName

/**
 * @author chy
 * 用于json转对象的类
 */
data class GetTextItem(
    @SerializedName("_lcattrs")
    val lcattrs: Any,
    @SerializedName("_lctext")
    val lctext: String,
    @SerializedName("_lctype")
    val lctype: Int
)