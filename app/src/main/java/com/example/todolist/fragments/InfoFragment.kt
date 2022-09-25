package com.example.todolist.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.leancloud.im.v2.LCIMMessageManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.todolist.R
import com.example.todolist.adapter.InfoAdapter
import com.example.todolist.adapter.MottoAdapter
import com.example.todolist.databinding.FragmentInfoBinding
import com.example.todolist.lc.CustomConversationEventHandler
import com.example.todolist.lc.CustomMessageHandler
import com.example.todolist.lc.loginChatServer
import com.example.todolist.model.MainViewModel
import com.example.todolist.utils.startRepeatingJob


class InfoFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentInfoBinding
    private val imageList = listOf(
        R.drawable.ic_setting,
        R.drawable.ic_notice,
        R.drawable.ic_ziliao,
        R.drawable.ic_community,
        R.drawable.ic_join,
        R.drawable.ic_customer
    )

    private val textList = listOf(
        "我的设置", "我的通知", "关于APP", "加入社区", "关于作者", "联系客服"
    )



    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model.shouldShowNavigationView.postValue(true)
        binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        binding.model = model
        binding.lifecycleOwner = this
        val infoAdapter = InfoAdapter()
        binding.infoRecyclerView.apply {
            adapter = infoAdapter
            infoAdapter.initAdapter(textList, imageList, lifecycleScope)
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        }


        val mottoAdapter = MottoAdapter()
        binding.mottoRecyclerView.apply {
            adapter = mottoAdapter
            mottoAdapter.initData(model.motto.value!!)
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            startRepeatingJob(2000, this)
        }

        model.motto.observe(viewLifecycleOwner) {
            binding.mottoRecyclerView.apply {
                adapter = mottoAdapter
                mottoAdapter.initData(model.motto.value!!)
                layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
                startRepeatingJob(2000, this)
            }
        }

        model.lcUri.observe(viewLifecycleOwner) {
            Glide.with(requireActivity())
                .load(it)
                .placeholder(requireActivity().getDrawable(R.drawable.photo))
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.imageView)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        loginChatServer(requireActivity(), model.currentUser!!.username)

//
//        //设置专栏栏和导航栏的底色，透明
//        window.statusBarColor = Color.TRANSPARENT
//        window.navigationBarColor = Color.TRANSPARENT
//        window.navigationBarDividerColor = Color.TRANSPARENT
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
    }
}
