package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cn.leancloud.LCFile
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.example.todolist.R
import com.example.todolist.databinding.FragmentUserNameBinding
import com.example.todolist.model.MainViewModel
import com.example.todolist.utils.uploadImageToLC

class UserNameFragment : SuperBottomSheetFragment() {

    private val model: MainViewModel by activityViewModels()
    private lateinit var binding:FragmentUserNameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUserNameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sureBtn.setOnClickListener {
            val name = binding.userview.text.toString()
            model.currentUser?.put("username", name)
            model.currentUser?.let { it1 -> model.updateUserInfo(view.context, it1) }
            model.userName = name
            model.welcomeStr.postValue(name)
            //再次上传图片
            uploadImageToLC(requireActivity(), model)
            findNavController().navigateUp()
            Toast.makeText(requireActivity(), "修改成功", Toast.LENGTH_SHORT).show()
        }
    }

}