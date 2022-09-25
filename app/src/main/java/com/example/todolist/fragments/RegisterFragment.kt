package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import cn.leancloud.LCUser
import com.example.todolist.R
import com.example.todolist.databinding.FragmentRegisterBinding
import com.example.todolist.model.MainViewModel

class RegisterFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.registerButton.setOnClickListener {
            if (binding.emailTextView.text.toString().endsWith("@qq.com", true)) {
                if (binding.userNameTextView.text.toString().length in 1..6) {
                    val aUSer = LCUser().apply {
                        username = binding.userNameTextView.text.toString()
                        password = binding.psdTextView.text.toString()
                        email = binding.emailTextView.text.toString()
                    }
                    model.signUpUser(requireActivity(), aUSer) {
                        if (it) {
                            findNavController().navigateUp()
                        }
                    }
                } else {
                    Toast.makeText(requireActivity(), "用户名必须由1至6个字符组成", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireActivity(), "请输入正确的qq邮箱", Toast.LENGTH_SHORT).show()
            }

        }


        binding.returnBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}