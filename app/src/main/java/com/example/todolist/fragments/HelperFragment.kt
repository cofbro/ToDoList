package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentHelperBinding
import com.example.todolist.utils.helperAnimator

class HelperFragment : Fragment() {


    private var num = 1
    private lateinit var binding: FragmentHelperBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelperBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        helperAnimator(binding.firstHelper)
        binding.button.setOnClickListener {
            when (num) {
                1 -> {
                    binding.button.text = "下一步"
                    helperAnimator(binding.secondHelper)
                    num++
                }
                2 -> {
                    helperAnimator(binding.thirdHelper)
                    num++
                }
                3 -> {
                    helperAnimator(binding.fourHelper)
                    num++
                }
                4 -> {
                    helperAnimator(binding.fiveHelper)
                    num++
                }
                5 -> {
                    helperAnimator(binding.sixHelper)
                    num++
                }
                6 -> {
                    binding.button.text = "探索更多功能"
                    helperAnimator(binding.sevenHelper)
                    num++
                }
                7 -> {
                    findNavController().navigate(R.id.action_helperFragment_to_homeFragment)
                }
            }
        }






    }

}