package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddContactsBinding


class AddContactsFragment : Fragment() {
    private lateinit var binding: FragmentAddContactsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContactsBinding.inflate(layoutInflater, container, false)





        return binding.root
    }


}