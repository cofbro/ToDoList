package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.todolist.R
import com.example.todolist.databinding.MessageItemLayoutBinding
import com.example.todolist.fragments.ChatFragmentArgs
import com.example.todolist.lc.Msg
import com.example.todolist.model.MainViewModel

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {
    private var msg: List<Msg> = emptyList()
    private lateinit var model: MainViewModel
    private lateinit var args: ChatFragmentArgs
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MessageItemLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (msg[position].messageType == Msg.GET) {
            holder.bindByGet(msg[position].message, args)
        } else {
            holder.bindBySend(msg[position].message, model)
        }
    }

    override fun getItemCount(): Int {
        return msg.size
    }

    class MyViewHolder(
        private val binding: MessageItemLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bindByGet(text: String, args: ChatFragmentArgs) {
            //隐藏
            binding.sendTextView.visibility = View.GONE
            binding.sendAvater.visibility = View.GONE
            binding.getTextView.visibility = View.VISIBLE
            binding.getAvatar.visibility = View.VISIBLE
            binding.getTextView.text = text
            Glide.with(binding.root.context)
                .load(args.user.avatarUrl)
                .placeholder(binding.root.context.getDrawable(R.drawable.photo))
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.getAvatar)
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bindBySend(text: String, model: MainViewModel) {
            binding.getTextView.visibility = View.GONE
            binding.getAvatar.visibility = View.GONE
            binding.sendTextView.visibility = View.VISIBLE
            binding.sendAvater.visibility = View.VISIBLE
            binding.sendTextView.text = text
            Glide.with(binding.root.context)
                .load(model.lcUri.value)
                .placeholder(binding.root.context.getDrawable(R.drawable.photo))
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.sendAvater)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(msgList: List<Msg>) {
        msg = msgList
        notifyDataSetChanged()
    }

    fun setMainViewModel(model: MainViewModel, args: ChatFragmentArgs) {
        this.model = model
        this.args = args
    }
}