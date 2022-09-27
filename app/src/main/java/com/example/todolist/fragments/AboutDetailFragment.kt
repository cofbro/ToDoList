package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapter.AboutDetailAdapter
import com.example.todolist.databinding.FragmentAboutDetailBinding


class AboutDetailFragment : Fragment() {
    private lateinit var binding:FragmentAboutDetailBinding
    private val privacyList = listOf(
        "最终用户许可协议","隐私政策","关于第三方开源库"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutDetailBinding.inflate(layoutInflater,container,false)
        val aboutDetailAdapter = AboutDetailAdapter()
        binding.aboutDetailRecyclerView.apply {
            adapter = aboutDetailAdapter
            aboutDetailAdapter.setData(privacyList,lifecycleScope)
            layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
        return binding.root
    }

}