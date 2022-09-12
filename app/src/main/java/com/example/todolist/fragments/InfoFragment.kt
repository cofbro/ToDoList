package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapter.InfoAdapter
import com.example.todolist.databinding.FragmentInfoBinding
import com.example.todolist.utils.SpacingItemDecoration
import com.example.todolist.utils.dp2px


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(layoutInflater, container, false)

        binding.infoRecyclerView.apply {
            adapter = InfoAdapter()
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            addItemDecoration(
                SpacingItemDecoration(
                    requireActivity().dp2px(8),
                    requireActivity().dp2px(20),
                    requireActivity().dp2px(5),
                    requireActivity().dp2px(30)
                )
            )
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}