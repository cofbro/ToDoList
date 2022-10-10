package com.example.todolist.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.db.Info
import com.example.todolist.db.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeModel(application: Application) : AndroidViewModel(application) {

    //选择时间的参数
    var day = ""
    var hour = ""
    var minute = ""
    var month = ""
    var millisecond = 0
    var num = MutableLiveData(0)

    private val repository = Repository(application)
    val modelList = MutableLiveData<List<Info>>(emptyList())
    private lateinit var binding: FragmentHomeBinding
    fun setBinding(mBinding: FragmentHomeBinding) {
        binding = mBinding
    }
    //如果没有待办事项，显示另一种风格
    private fun showTheEmptyClipBoard() {
        binding.bottomTextView.visibility = View.VISIBLE
        binding.textView.visibility = View.VISIBLE
        binding.clipBoard.visibility = View.VISIBLE
    }

    private fun hideTheEmptyClipBoard() {
        binding.bottomTextView.visibility = View.GONE
        binding.textView.visibility = View.GONE
        binding.clipBoard.visibility = View.GONE
    }

    //动态创建recyclerView
    fun setRecyclerView(context: Context): RecyclerView {

        val recyclerView = RecyclerView(context).apply {
            id = R.id.recyclerView
        }
        val constraintLayout = ConstraintLayout.LayoutParams(
            0, 0
        ).apply {
            startToStart = R.id.container
            endToEnd = R.id.container
            topToBottom = R.id.titleView
            bottomToBottom = R.id.container
        }
        binding.container.addView(recyclerView, constraintLayout)

        return recyclerView
    }
    //将待办事项数据存入本地数据库
    fun insertModel(todoStr: String, color: String, time: String) {
        num.postValue(num.value!! + 1)
        viewModelScope.launch(Dispatchers.IO) {
            val model = Info(0, todoStr, color, day, month, hour, minute, 0,0,millisecond)
            repository.insertModel(model)
        }
    }
    //得到所有待办事项的数据
    fun getAllModel() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllModels().collect {
                modelList.postValue(it)
            }
        }
    }
    //删除一个待办事项
    fun deleteModel(info: Info) {
        num.postValue(num.value!! - 1)
        viewModelScope.launch(Dispatchers.IO) {
            delay(400)
            repository.deleteModel(info)
        }
    }
    //更新待办事项
    fun updateModel(info: Info) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateModel(info)
        }
    }

    //判断是否显示recyclerView
    fun judgeShowRecyclerView() {
        if (modelList.value!!.isNotEmpty()) {
            hideTheEmptyClipBoard()
        } else {
            showTheEmptyClipBoard()
        }
    }

    @SuppressLint("SetTextI18n")
    fun showNumTextView() {
        if (modelList.value!!.isNotEmpty()) {
            binding.numTextView.text = "今日还有 ${modelList.value!!.size} 件事未完成哦！"
        } else {
            binding.numTextView.text = "今日还未有待办的事项哦"
        }
    }


}