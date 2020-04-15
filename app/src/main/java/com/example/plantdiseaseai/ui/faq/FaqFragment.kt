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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.button.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE){
            Timber.i("Yes it is")
            if (resultCode == RESULT_OK && data != null){
                val bitmap = data.extras!!.get("data") as Bitmap
                val scaledBitmap = Bitmap.createScaledBitmap(bitmap,1000,1000,true)
                val path = MediaStore.Images.Media.insertImage(context?.contentResolver,bitmap,"Title",null)
                val uri = Uri.parse(path)
                CropImage.activity(uri)
                    .start(requireContext(),this)
            }
        }
    }
}