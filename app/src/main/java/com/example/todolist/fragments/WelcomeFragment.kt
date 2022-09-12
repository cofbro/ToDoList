package com.example.todolist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentWelcomeBinding
import com.example.todolist.model.MainViewModel
import com.example.todolist.utils.loadData

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)


        model.shouldShowNavigationView.postValue(false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isFirstLogin: String? = null
        loadData(requireContext(), lifecycleScope) {
            isFirstLogin = it
        }


        binding.start.setOnClickListener {
            Log.d("chy","$isFirstLogin")
            if (isFirstLogin == null) {
                findNavController().navigate(R.id.action_welcomeFragment_to_helperFragment)
            } else {
                findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
            }
        }
    }

}