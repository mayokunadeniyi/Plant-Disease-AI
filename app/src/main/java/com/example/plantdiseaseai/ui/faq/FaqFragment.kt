package com.example.plantdiseaseai.ui.faq

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plantdiseaseai.databinding.FragmentFaqBinding
import com.example.plantdiseaseai.utils.CAMERA_REQUEST_CODE
import com.example.plantdiseaseai.utils.setUpToolbar
import com.theartofdev.edmodo.cropper.CropImage
import timber.log.Timber
import java.io.ByteArrayOutputStream

/**
 * Created by Mayokun Adeniyi on 11/04/2020.
 */

class FaqFragment : Fragment(){
    private lateinit var binding: FragmentFaqBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFaqBinding.inflate(layoutInflater)
        setUpToolbar(showActionBar = true)
        return binding.root
    }

}