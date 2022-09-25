package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import cn.leancloud.LCFile
import com.example.todolist.R
import com.example.todolist.databinding.FragmentPhotoPickerBinding
import com.example.todolist.model.MainViewModel
import com.example.todolist.utils.insertImage
import com.example.todolist.utils.uploadImageToLC
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.pixFragment
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


class PhotoPickerFragment : Fragment() {

    private lateinit var binding: FragmentPhotoPickerBinding
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPhotoPickerBinding.inflate(layoutInflater, container, false)
        showImagePix()
        return binding.root
    }


    private fun showImagePix() {
        val options = Options().apply {
            ratio = Ratio.RATIO_AUTO                                    //Image/video capture ratio
            count =
                1                                                   //Number of images to restrict selection count
            spanCount = 4                                               //Number for columns in grid
            path =
                "Pix/Camera"                                         //Custom Path For media Storage
            isFrontFacing =
                false                                       //Front Facing camera on start
            videoOptions.videoDurationLimitInSeconds =
                10               //Duration for video recording
            mode = Mode.Picture
        }

        val fragment = pixFragment(options) {
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> {
                    it.data.forEach { uri ->
                        val context = requireActivity()
                        insertImage(context, uri, model.userName) { path ->
                            model.localAbsolutePath = path
                            uploadImageToLC(context, model)
                        }
                        findNavController().navigateUp()
                    }
                }
                PixEventCallback.Status.BACK_PRESSED -> {

                }
            }
        }


        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.rootContainerInMediaPicker, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        val window = requireActivity().window
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowCompat.getInsetsController(window, binding.root).show(WindowInsetsCompat.Type.systemBars())
    }
}