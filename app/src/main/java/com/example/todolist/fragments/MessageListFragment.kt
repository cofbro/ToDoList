package com.example.todolist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.SearchUserDialog
import com.example.todolist.adapter.UserListAdapter
import com.example.todolist.databinding.FragmentAddContactsBinding
import com.example.todolist.databinding.FragmentMessageListBinding
import com.example.todolist.lc.createConversationWithUser
import com.example.todolist.lc.loginChatServer
import com.example.todolist.model.ChatModel
import com.example.todolist.model.MainViewModel


class MessageListFragment : Fragment() {


    private val model: MainViewModel by activityViewModels()
    private val chatModel = ChatModel.getInstance()
    private val userListAdapter = UserListAdapter()
    private lateinit var binding: FragmentMessageListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //隐藏导航栏
        model.shouldShowNavigationView.postValue(false)
        binding = FragmentMessageListBinding.inflate(layoutInflater, container, false)

        //登录
        loginChatServer(chatModel, model.currentUser!!.username) {
            if (it != null) {
                chatModel.client = it
            }
        }
        //adapter初始数据
        userListAdapter.setData(chatModel.userList)
        chatModel.userMutableList.observe(viewLifecycleOwner) {
            userListAdapter.setData(it)
        }

        binding.userListRecyclerView.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //显示加好友的dialog
        val searchDialog = SearchUserDialog(requireActivity(), chatModel)


        binding.addUser.setOnClickListener {
            searchDialog.showDialog()
        }
        searchDialog.clickSearchBtn()
    }


}