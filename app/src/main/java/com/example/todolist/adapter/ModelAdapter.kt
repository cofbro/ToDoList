package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.TodoListView
import com.example.todolist.databinding.ItemLayoutBinding
import com.example.todolist.db.Info
import com.example.todolist.model.HomeModel

class ModelAdapter : RecyclerView.Adapter<ModelAdapter.MyViewHolder>() {
    private lateinit var homeModel: HomeModel
    private var dataList = emptyList<Info>()
    private lateinit var binding: ItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val info = dataList[position]
        holder.bind(info, homeModel)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: Info, model: HomeModel) {
            binding.todoListView.rightNum = info.rightNum
            binding.todoListView.leftNum = info.leftNum
            binding.todoListView.remainTheStatusAndImage(info.todoStr)
            binding.todoListView.getCurrentInfo(info)
            binding.todoListView.getBinding(binding)
            binding.todoListView.setColor(info.color)
            binding.todoListView.setContent(info.todoStr)
            binding.todoListView.getMillisecond(info.millisecond)
            binding.todoListView.initTime("${info.hour} : ${info.minute}")
            binding.todoListView.updateUI = {
                model.deleteModel(info)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Info>) {
        dataList = data
        notifyDataSetChanged()
    }


    fun setModel(model: HomeModel) {
        homeModel = model
    }

}