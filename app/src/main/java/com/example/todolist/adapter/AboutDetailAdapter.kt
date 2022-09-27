package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.AboutDetailItemBinding
import kotlinx.coroutines.*

class AboutDetailAdapter : RecyclerView.Adapter<AboutDetailAdapter.MyViewHolder>() {
    private var privacyList = emptyList<String>()
    private lateinit var lifecycleScope: CoroutineScope

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<String>,newLifecycleScope: CoroutineScope){
        lifecycleScope = newLifecycleScope
        privacyList = newData
        notifyDataSetChanged()//监听数据变化
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AboutDetailItemBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.privacyText.text = privacyList[position]
        holder.privacyText.setOnClickListener {
            enterNext(position,it)
        }
    }

    override fun getItemCount(): Int {
        return privacyList.size
    }

    class MyViewHolder(private val binding: AboutDetailItemBinding):
        RecyclerView.ViewHolder(binding.root){
            val privacyText = binding.itemText
    }

    fun enterNext(int:Int,view:View){
        when(int){
            0-> lifecycleScope.launch(Dispatchers.IO) {
                delay(200)
                withContext(Dispatchers.Main){
                    view.findNavController().navigate(R.id.action_aboutDetailFragment_to_aboutUserPermissionFragment)
                }
            }
            1-> lifecycleScope.launch(Dispatchers.IO) {
                delay(200)
                withContext(Dispatchers.Main){
                    view.findNavController().navigate(R.id.action_aboutDetailFragment_to_aboutPrivacyPolicyFragment)
                }
            }
            2-> lifecycleScope.launch(Dispatchers.IO) {
                delay(200)
                withContext(Dispatchers.Main){
                    view.findNavController().navigate(R.id.action_aboutDetailFragment_to_aboutStorehouseFragment2)
                }
            }
        }
    }
}