package com.example.todolist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapter.InfoAdapter
import com.example.todolist.databinding.FragmentInfoBinding
import com.example.todolist.utils.SpacingItemDecoration
import com.example.todolist.utils.dp2px


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private val imageList = listOf(
        R.drawable.ic_setting,
        R.drawable.ic_notice,
        R.drawable.ic_ziliao,
        R.drawable.ic_community,
        R.drawable.ic_join,
        R.drawable.ic_customer
    )

    private val textList = listOf(
        "我的设置","我的通知","关于APP","加入社区","关于作者","联系客服"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(layoutInflater, container, false)

        val infoAdapter = InfoAdapter()
        binding.infoRecyclerView.apply {
            adapter = infoAdapter
            infoAdapter.initAdapter(textList, imageList, lifecycleScope)
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        }


        return binding.root
    }


}