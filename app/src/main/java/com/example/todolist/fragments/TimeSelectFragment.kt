package com.example.todolist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.model.BottomSheetModel
import com.loper7.date_time_picker.dialog.CardDatePickerDialog

class TimeSelectFragment : Fragment() {


    private val model: BottomSheetModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_time_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        CardDatePickerDialog.builder(requireActivity())
            .setTitle("请选择合适的时间")
            .showBackNow(true)
            .setWrapSelectorWheel(true)
            .showDateLabel(true)
            .showFocusDateInfo(true)
            .setLabelText("年","月","日","时","分")
            .setOnChoose("选择"){ millisecond ->
                model.saveTime(millisecond)
                findNavController().navigate(R.id.action_timeSelectFragment_to_bottomSheetFragment)
            }
            .setOnCancel("关闭") {
                findNavController().navigate(R.id.action_timeSelectFragment_to_bottomSheetFragment)
            }
            .build().show()

    }

}