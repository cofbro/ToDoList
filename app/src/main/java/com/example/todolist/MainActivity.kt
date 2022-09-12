package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.model.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_ToDoList)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        model.shouldShowNavigationView.postValue(false)


        binding.model = model
        binding.lifecycleOwner = this


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)


//        binding.bottomNavigationView.setOnItemSelectedListener {
//            if (it.itemId == R.id.bottomSheetFragment) {
//                navHostFragment.findNavController().navigate(R.id.action_homeFragment_to_bottomSheetFragment)
//            } else if (it.itemId == R.id.detailFragment) {
//                navHostFragment.findNavController().navigate(R.id.)
//            } else if (it.itemId == R.id.detailFragment) {
//                navHostFragment.findNavController().navigate(R.id.action_homeFragment_to_infoFragment)
//            }
//            true
//        }

    }






}