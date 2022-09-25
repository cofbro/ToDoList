package com.example.todolist.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.leancloud.im.v2.LCIMConversation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.todolist.R
import com.example.todolist.adapter.MessageAdapter
import com.example.todolist.databinding.FragmentChatBinding
import com.example.todolist.lc.*
import com.example.todolist.model.ChatModel
import com.example.todolist.model.MainViewModel


class ChatFragment : Fragment() {

    private val chatModel = ChatModel.getInstance()
    private var conversation: LCIMConversation? = null
    private val model: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentChatBinding
    private val messageAdapter = MessageAdapter()
    private val args: ChatFragmentArgs by navArgs()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        //更新界面头像与名字
        Glide.with(binding.root.context)
            .load(args.user.avatarUrl)
            .placeholder(binding.root.context.getDrawable(R.drawable.photo))
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(binding.userAvatar)
        binding.username.text = args.user.username

        //adapter传数据
        messageAdapter.setMainViewModel(model, args)
        chatModel.receiveMessageMutableList.observe(viewLifecycleOwner) {
            val msgList =
                chatModel.msgListFilter(it, args.user.username, model.currentUser!!.username)
            messageAdapter.setData(msgList)
        }

        binding.recyclerView2.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        loginChatServer(requireActivity(),model) {
//            if (it != null) {
//                createConversationWithUser(it, model, "ccc") { c ->
//                    conversation = c
//                }
//            }
//
//        }



            createConversationWithUser(chatModel.client!!, model, args.user.username) { c ->
                conversation = c
            }




        binding.button2.setOnClickListener {
            val text = binding.chatEditView.text.toString()
            if (conversation != null) {
                sendMegToServer(text, conversation)
                val msg =
                    Msg(text, Msg.SEND, conversation!!.name)
                chatModel.saveReceiveMessage(msg)
                chatModel.receiveMessageMutableList.postValue(chatModel.receiveMessageList)
                binding.chatEditView.text = null
                binding.recyclerView2.scrollToPosition(messageAdapter.itemCount - 1)
            }

        }
    }
}
