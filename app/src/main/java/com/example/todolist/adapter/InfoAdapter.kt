package com.example.todolist.adapter

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.InfoItemLayoutBinding
import kotlinx.coroutines.*
import java.util.*

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
        holder.bind(textList[position], imageList[position], lifecycleScope)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class MyViewHolder(private val binding: InfoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(text: String, image: Int, lifecycleScope: CoroutineScope) {
            binding.iconImageView.background =
                binding.root.context.getDrawable(image)
            binding.settingTextView.text = text
            binding.recyclerConstraint.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    delay(200)
                    withContext(Dispatchers.Main) {
                        it.findNavController().navigate(R.id.action_infoFragment_to_settingFragment)
                    }
                }
            }
        }

    }

    fun initAdapter(textList: List<String>, imageList: List<Int>, lifecycleScope: CoroutineScope) {
        this.imageList = imageList
        this.textList = textList
        this.lifecycleScope = lifecycleScope
    }

}