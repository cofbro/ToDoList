package com.example.todolist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.InfoItemLayoutBinding
import kotlinx.coroutines.*

class InfoAdapter : RecyclerView.Adapter<InfoAdapter.MyViewHolder>() {
    private var textList = emptyList<String>()
    private var imageList = emptyList<Int>()
    private lateinit var lifecycleScope: CoroutineScope

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InfoItemLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(textList[position], imageList[position], lifecycleScope, position)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class MyViewHolder(private val binding: InfoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(text: String, image: Int, lifecycleScope: CoroutineScope, position: Int) {
            binding.iconImageView.background =
                binding.root.context.getDrawable(image)
            binding.settingTextView.text = text
//            if (position == 0) {
//                itemView.setOnClickListener {
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        delay(200)
//                        withContext(Dispatchers.Main) {
//                            it.findNavController().navigate(R.id.action_infoFragment_to_settingFragment)
//                        }
//                    }
//                }
//            }
            binding.recyclerConstraint.setOnClickListener {
                it.findNavController().navigate(R.id.action_infoFragment_to_messageListFragment)
            }
        }


    }

    fun initAdapter(textList: List<String>, imageList: List<Int>, lifecycleScope: CoroutineScope) {
        this.imageList = imageList
        this.textList = textList
        this.lifecycleScope = lifecycleScope
    }
}