package com.example.todolist.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.example.todolist.R
import com.example.todolist.databinding.FragmentBottomSheetBinding
import com.example.todolist.model.BottomSheetModel
import com.example.todolist.model.HomeModel
import com.example.todolist.utils.loadData
import com.example.todolist.utils.saveData
import com.google.android.material.chip.Chip


class BottomSheetFragment : SuperBottomSheetFragment() {

    private var data: String? = ""
    private val homeModel: HomeModel by activityViewModels()
    private val model: BottomSheetModel by activityViewModels()
    private lateinit var binding: FragmentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)



        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadData(requireActivity(), lifecycleScope) {
            data = it
            if (data != "") {
                binding.editText.setText(data)
                val timeStr = model.getTimeStr()
                if (timeStr != "") {
                    val day = timeStr.substring(6,8)
                    val hour = timeStr.substring(8,10)
                    val minute = timeStr.substring(10,12)
                    val month = timeStr.substring(4,6)
                    homeModel.day = day
                    homeModel.hour = hour
                    homeModel.minute = minute
                    homeModel.month = month
                    binding.timePickerView.text = "$month 月 $day 日   $hour : $minute"
                }

            }
        }




        val index = model.colorList.indexOf(model.getChipStr())
        if (index >= 0 && index < model.colorList.size) {
            (binding.chipGroup.getChildAt(index) as Chip).isChecked = true
        }


        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                binding.one.id -> {
                    model.saveChipID(0)
                }
                binding.two.id -> {
                    model.saveChipID(1)
                }
                binding.three.id -> {
                    model.saveChipID(2)
                }
                binding.four.id -> {
                    model.saveChipID(3)
                }
                binding.five.id -> {
                    model.saveChipID(4)
                }
                binding.six.id -> {
                    model.saveChipID(5)
                }
            }
        }

        binding.timePickerView.setOnClickListener {
            saveData(requireActivity(), binding.editText.text.toString(), lifecycleScope)
            findNavController().navigate(R.id.action_bottomSheetFragment_to_timeSelectFragment)
        }

        binding.button.setOnClickListener {
            model.saveTodoStr(binding.editText.text.toString())
            homeModel.insertModel(model.getTodoStr(), model.getChipStr(), model.getTimeStr())
            saveData(requireActivity(), binding.editText.text.toString(), lifecycleScope)
            findNavController().navigateUp()
        }
    }




}