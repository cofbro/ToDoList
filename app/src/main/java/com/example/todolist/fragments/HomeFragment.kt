package com.example.todolist.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.TodoListView
import com.example.todolist.adapter.ModelAdapter
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.db.Info
import com.example.todolist.model.HomeModel
import com.example.todolist.model.MainViewModel

class HomeFragment : Fragment() {

    private val homeModel: HomeModel by activityViewModels()
    private val model: MainViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentHomeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        model.shouldShowNavigationView.postValue(true)
        homeModel.setBinding(binding)

        homeModel.getAllModel()




        recyclerView =
            homeModel.setRecyclerView(requireActivity())


        homeModel.modelList.observe(viewLifecycleOwner) {
            homeModel.showNumTextView()
            homeModel.judgeShowRecyclerView()
            val adapter = ModelAdapter()
            adapter.setData(it)
            adapter.setModel(homeModel)
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter
        }

        return binding.root
    }


    override fun onPause() {
        super.onPause()
        if (homeModel.modelList.value!!.isNotEmpty())
            for (i in 0 until homeModel.modelList.value!!.size) {
                val todoListView = recyclerView.layoutManager!!.findViewByPosition(i)!!.findViewById(
                    R.id.todoListView) as TodoListView
                val info = Info(
                    todoListView.info.modelId,
                    todoListView.info.todoStr,
                    todoListView.info.color,
                    todoListView.info.day,
                    todoListView.info.month,
                    todoListView.info.hour,
                    todoListView.info.minute,
                    todoListView.leftNum,
                    todoListView.rightNum,
                    todoListView.info.millisecond
                )
                homeModel.updateModel(info)
            }

    }

}