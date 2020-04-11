package com.example.plantdiseaseai.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plantdiseaseai.databinding.FragmentFaqBinding

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
}