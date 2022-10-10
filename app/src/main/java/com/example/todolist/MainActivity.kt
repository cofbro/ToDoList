package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import cn.leancloud.LCQuery
import cn.leancloud.LCUser
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.db.Repository
import com.example.todolist.lc.QueryImageUrl
import com.example.todolist.model.ChatModel
import com.example.todolist.model.MainViewModel
import com.example.todolist.utils.loadUser
import com.example.todolist.utils.loadUserName
import com.example.todolist.utils.loadUserPsd
import kotlinx.coroutines.*

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


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
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


        //从本地数据库加载当前用户信息（方法有点差，但是先不改）
        loadUser(this) {
            if (it == true) {
                loadUserName(this) { n ->
                    val name = n!!
                    loadUserPsd(this) { psd ->
                        model.psd = psd!!
                        model.loginWithUserNameAndPassword(this, name, psd) { b ->
                            if (b) {
                                model.isLogin.postValue(true)
                                model.welcomeStr.postValue(name)
                                model.userName = name
                                QueryImageUrl().queryImageFromLC(name) { uri ->
                                    model.lcUri.postValue(uri)
                                }
                            }
                        }
                    }
                }
            }
        }

        val chatModel = ChatModel.getInstance()
        val repository = Repository(this)
        //从本地加载用户列表信息
        lifecycleScope.launch(Dispatchers.IO) {
            val list = repository.getAllUserInfo()
            withContext(Dispatchers.Main) {
                list.forEach {
                    val user = ChatUser(it.name, it.imageUrl)
                    chatModel.userList.add(user)
                }
                //chatModel.userMutableList.postValue(chatModel.userList)
            }
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }


}