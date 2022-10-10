package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cn.leancloud.LCUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.todolist.ChatUser
import com.example.todolist.R
import com.example.todolist.databinding.UserItemLayoutBinding
import com.example.todolist.fragments.MessageListFragmentDirections

/**
 * @author chy
 * 用户列表adapter
 */
class UserListAdapter : RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {
    private lateinit var userInfoList: List<ChatUser>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserItemLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userInfoList[position])
    }

    override fun getItemCount(): Int {
        return userInfoList.size
    }

    class MyViewHolder(private val binding: UserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(user: ChatUser) {
            //显示头像以及用户名
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .placeholder(binding.root.context.getDrawable(R.drawable.photo))
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.userAvatar)
            binding.usernameView.text = user.username
            binding.root.setOnClickListener {
                val action = MessageListFragmentDirections.actionMessageListFragmentToChatFragment(user)
                binding.root.findNavController().navigate(action)
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(userList: List<ChatUser>) {
        this.userInfoList = userList
        notifyDataSetChanged()
    }
}