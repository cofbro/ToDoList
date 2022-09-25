package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.todolist.PhoneEditText
import com.example.todolist.R
import com.example.todolist.databinding.FragmentLoginBinding
import com.example.todolist.events.ClickEvents
import com.example.todolist.model.MainViewModel

class LoginFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        model.shouldShowNavigationView.postValue(false)
        binding.phoneText = binding.phoneEditText
        binding.clickEvent = ClickEvents()
        binding.model = model
        binding.lifecycleOwner = this
        binding.login = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imageView.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}