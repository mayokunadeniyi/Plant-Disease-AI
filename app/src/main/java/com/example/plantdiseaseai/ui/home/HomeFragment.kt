package com.example.plantdiseaseai.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.plantdiseaseai.R
import com.example.plantdiseaseai.databinding.FragmentHomeBinding
import com.example.plantdiseaseai.utils.setUpToolbar

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setUpToolbar(showActionBar = true, showBackButton = false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cameraGrid.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cameraFragment)
        }

        binding.galleryGrid.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_galleryFragment)
        }

        binding.faqGrid.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_faqFragment)
        }

        binding.aboutAppGrid.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_aboutAppFragment)
        }
    }

}
