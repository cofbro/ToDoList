package com.example.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.todolist.databinding.FragmentAddContactsBinding
import com.example.todolist.model.ChatModel
import com.example.todolist.model.MainViewModel

class SearchUserDialog(val context: Context, val model: ChatModel) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val binding = FragmentAddContactsBinding.inflate(inflater)
    fun showDialog() {
        val dialog = AlertDialog
            .Builder(context)
            .setView(binding.root)
            .setCancelable(true)
            .create()
        dialog.show()
    }

    fun clickSearchBtn() {
        binding.searchBtn.setOnClickListener {
            model.searchUserByUsername(context, binding.searchNameEditText.text.toString()) {
                if (it) {
                    binding.addContactBtn.visibility = View.VISIBLE
                } else {
                    binding.addContactBtn.visibility = View.GONE
                }
            }
        }
    }

}