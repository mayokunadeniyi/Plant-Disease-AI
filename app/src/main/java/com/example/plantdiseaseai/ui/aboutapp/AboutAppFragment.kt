package com.example.plantdiseaseai.ui.aboutapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plantdiseaseai.databinding.FragmentAboutAppBinding
import com.example.plantdiseaseai.utils.setUpToolbar

/**
 * Created by Mayokun Adeniyi on 11/04/2020.
 */

class AboutAppFragment : Fragment(){
    private lateinit var binding: FragmentAboutAppBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutAppBinding.inflate(layoutInflater)
        setUpToolbar(showActionBar = true)
        return binding.root
    }
}