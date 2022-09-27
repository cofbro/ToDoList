package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAboutBinding
import kotlinx.coroutines.*

class AboutFragment : Fragment() {
    private lateinit var binding :FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentAboutBinding.inflate(layoutInflater,container,false)
        binding.enterText.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                delay(200)
                withContext(Dispatchers.Main){
                    it.findNavController().navigate(R.id.action_aboutFragment_to_aboutDetailFragment)
                }
            }
        }
        return binding.root
    }


}