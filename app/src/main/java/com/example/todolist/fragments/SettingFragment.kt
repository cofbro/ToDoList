package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todolist.R
import com.example.todolist.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var bining: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bining = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return bining.root
    }
}