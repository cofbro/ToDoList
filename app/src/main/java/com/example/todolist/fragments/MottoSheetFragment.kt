package com.example.todolist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.example.todolist.R
import com.example.todolist.databinding.FragmentMottoSheetBinding
import com.example.todolist.model.MainViewModel

class MottoSheetFragment : SuperBottomSheetFragment() {

    private val model: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMottoSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMottoSheetBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sureBtn.setOnClickListener {
            val motto = binding.editTextTextPersonName.text.toString()
            model.currentUser?.put("motto", motto)
            model.currentUser?.let { it1 -> model.updateUserInfo(view.context, it1) }
            model.motto.postValue(motto)
            findNavController().navigateUp()
            Toast.makeText(requireActivity(), "修改成功", Toast.LENGTH_SHORT).show()
        }
    }

}