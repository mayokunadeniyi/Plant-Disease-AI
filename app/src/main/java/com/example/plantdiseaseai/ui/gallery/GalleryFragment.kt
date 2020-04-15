package com.example.plantdiseaseai.ui.gallery

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.plantdiseaseai.R
import com.example.plantdiseaseai.databinding.FragmentGalleryBinding
import com.example.plantdiseaseai.utils.Classifier
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import timber.log.Timber
import www.sanju.motiontoast.MotionToast

/**
 * Created by Mayokun Adeniyi on 11/04/2020.
 */

class GalleryFragment : Fragment(){
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var classifier: Classifier

    private val modelPath = "plant_disease_model.tflite"
    private val labelPath = "plant_labels.txt"
    private val inputSize = 299

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        classifier = Classifier(requireActivity().assets,modelPath,labelPath,inputSize)
        binding.selectImage.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(requireContext(),this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK){
                val resultUri = result.uri
                Glide.with(this)
                    .load(resultUri)
                    .into(binding.selectImage)
                MotionToast.createToast(
                    requireActivity(),
                    "Your image is set!",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(
                        requireContext(),
                        R.font.googlesans
                    ))
                analyzeImage(resultUri)
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                val error = result.error
                Timber.e(error)
            }
        }
    }

    private fun analyzeImage(uri: Uri) {
        binding.galleryAnalyzeBtn.visibility = View.VISIBLE
        binding.galleryAnalyzeBtn.setOnClickListener {
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,uri)
            val result = classifier.recognizeImage(bitmap).firstOrNull()
            FancyGifDialog.Builder(requireActivity())
                .setTitle("Here is your result!")
                .setMessage("Crop result: ${result?.title} \n\n Confidence: ${result?.confidence}")
                .setGifResource(R.drawable.complete_gif)
                .setNegativeBtnText("Cancel")
                .setPositiveBtnText("Retry")
                .isCancellable(true)
                .OnPositiveClicked { FancyGifDialogListener {  } }
                .build()
        }
    }
}