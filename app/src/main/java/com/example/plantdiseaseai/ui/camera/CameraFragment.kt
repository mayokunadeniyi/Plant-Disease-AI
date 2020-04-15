package com.example.plantdiseaseai.ui.camera

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.plantdiseaseai.R
import com.example.plantdiseaseai.databinding.FragmentCameraBinding
import com.example.plantdiseaseai.utils.CAMERA_REQUEST_CODE
import com.example.plantdiseaseai.utils.Classifier
import com.example.plantdiseaseai.utils.REQUEST_CODE_PERMISSIONS
import com.example.plantdiseaseai.utils.REQUIRED_PERMISSIONS
import com.google.android.material.snackbar.Snackbar
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import es.dmoral.toasty.Toasty
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CameraFragment : Fragment() {
    private lateinit var classifier: Classifier
    private lateinit var binding: FragmentCameraBinding
    private lateinit var currentPhotoPath: String

    private val modelPath = "plant_disease_model.tflite"
    private val labelPath = "plant_labels.txt"
    private val inputSize = 299

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        classifier = Classifier(requireActivity().assets, modelPath, labelPath, inputSize)
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(), REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun startCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Timber.e(ex)
                    null
                }

                photoFile?.also {
                    val photoUri: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.plantdiseaseai",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
                }
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = this.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        currentPhotoPath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && bitmap != null) {
                    val f = File(currentPhotoPath)
                    val uri = Uri.fromFile(f)
                    CropImage.activity(uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(requireContext(), this)
                } else if (bitmap == null) {
                    Toast.makeText(requireContext(), "No image found!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_cameraFragment_to_homeFragment)
                }
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri = result.uri
                    Glide.with(this)
                        .load(resultUri)
                        .into(binding.cropImageView)
                    Toasty.success(requireContext(), "Image is set!", Snackbar.LENGTH_SHORT).show()
                    analyzeImage(resultUri)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                    Timber.e(error)
                    Toasty.error(requireContext(), "Oops! an error occurred", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

    private fun analyzeImage(uri: Uri) {
        binding.cameraAnalyzeBtn.visibility = View.VISIBLE
        binding.cameraAnalyzeBtn.setOnClickListener {
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            val result = classifier.recognizeImage(bitmap).firstOrNull()
            FancyGifDialog.Builder(requireActivity())
                .setTitle("Here is your result!")
                .setMessage("Crop result: ${result?.title} \n\n Confidence: ${result?.confidence}")
                .setGifResource(R.drawable.complete_gif)
                .setNegativeBtnText("Cancel")
                .setPositiveBtnText("Retry")
                .isCancellable(true)
                .OnPositiveClicked { FancyGifDialogListener { startCamera() } }
                .build()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Snackbar.make(
                    binding.root,
                    "Permissions not granted by the user.",
                    Snackbar.LENGTH_SHORT
                ).show()
                this.activity?.finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

}
