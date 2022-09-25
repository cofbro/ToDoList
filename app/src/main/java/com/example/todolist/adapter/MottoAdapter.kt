package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.MottoItemLayuotBinding

class MottoAdapter : RecyclerView.Adapter<MottoAdapter.MyViewHolder>() {
    private var motto = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MottoItemLayuotBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(motto)
    }

    override fun getItemCount(): Int {
        return 2
    }

    class MyViewHolder(private val binding: MottoItemLayuotBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(motto: String) {
            binding.mottoTextView.text = motto
        }
    }


    fun initData(motto: String) {
        this.motto = motto
    }

}