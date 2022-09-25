package com.example.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentSettingBinding
import com.example.todolist.lc.loginChatServer
import com.example.todolist.model.MainViewModel
import com.example.todolist.utils.postDelay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    private lateinit var bining: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bining = FragmentSettingBinding.inflate(layoutInflater, container, false)
        model.shouldShowNavigationView.postValue(false)
        return bining.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bining.uidView.setOnClickListener {
            postDelay(150) {
                if (model.isLogin.value!!) {
                    Toast.makeText(context, "已登录", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.action_settingFragment_to_loginFragment)
                }
            }
        }
        bining.avatarView.setOnClickListener {
            if (model.isLogin.value == true) {
                postDelay(150) {
                    findNavController().navigate(R.id.action_settingFragment_to_photoPickerFragment)
                }
            } else {
                Toast.makeText(requireActivity(), "您还未登陆", Toast.LENGTH_SHORT).show()
            }
        }

        bining.quitButton.setOnClickListener {
            model.quit(requireActivity())
        }

        bining.mottoTextView.setOnClickListener {
            if (model.isLogin.value == true) {
                findNavController().navigate(R.id.action_settingFragment_to_mottoSheetFragment)
            } else {
                Toast.makeText(requireActivity(), "您还未登陆", Toast.LENGTH_SHORT).show()
            }

        }

        bining.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        bining.usernameView.setOnClickListener {
            if (model.isLogin.value == true) {
                findNavController().navigate(R.id.action_settingFragment_to_userNameFragment)
            } else {
                Toast.makeText(requireActivity(), "您还未登陆", Toast.LENGTH_SHORT).show()
            }

        }

        bining.psdView.setOnClickListener {
            if (model.isLogin.value == true) {
                model.currentUser?.let { it1 -> model.changeThePassword(requireActivity(), it1) }
            } else {
                Toast.makeText(requireActivity(), "您还未登陆", Toast.LENGTH_SHORT).show()
            }
        }


        bining.themeView.setOnClickListener {
            loginChatServer(requireActivity(), model) {
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        model.shouldShowNavigationView.postValue(true)
    }
}