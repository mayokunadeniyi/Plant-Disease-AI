package com.example.plantdiseaseai.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plantdiseaseai.databinding.FragmentGalleryBinding

/**
 * Created by Mayokun Adeniyi on 11/04/2020.
 */

class GalleryFragment : Fragment(){
    private lateinit var binding: FragmentGalleryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }
}