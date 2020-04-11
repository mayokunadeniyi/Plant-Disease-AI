package com.example.plantdiseaseai.ui.camera

import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plantdiseaseai.R
import com.example.plantdiseaseai.databinding.FragmentCameraBinding
import com.example.plantdiseaseai.utils.REQUEST_CODE_PERMISSIONS
import com.example.plantdiseaseai.utils.REQUIRED_PERMISSIONS
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater)

        if (allPermissionsGranted()){
            binding.viewFinder.post{ startCamera()}
        }else{
            ActivityCompat.requestPermissions(this.requireActivity(), REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS)
        }

        binding.viewFinder.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            updateTransform()
        }


        return binding.root
    }


    private fun startCamera(){
        // Create configuration object for the viewfinder use case
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
        }.build()


        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = binding.viewFinder.parent as ViewGroup
            parent.removeView(binding.viewFinder)
            parent.addView(binding.viewFinder, 0)

            binding.viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        // Bind use cases to lifecycle
        CameraX.bindToLifecycle(viewLifecycleOwner, preview)
    }

    private fun updateTransform(){
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = binding.viewFinder.width / 2f
        val centerY = binding.viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(binding.viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        binding.viewFinder.setTransform(matrix)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS){
            if (allPermissionsGranted()){
                binding.viewFinder.post{startCamera()}
            }else{
                Snackbar.make(binding.root,"Permissions not granted by the user.",Snackbar.LENGTH_SHORT).show()
                this.activity?.finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(),it) == PackageManager.PERMISSION_GRANTED
    }

}
