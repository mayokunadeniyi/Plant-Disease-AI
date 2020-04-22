package com.example.plantdiseaseai.ui.onboarding

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.plantdiseaseai.R
import com.example.plantdiseaseai.databinding.FragmentOnBoardingBinding
import com.example.plantdiseaseai.utils.SharedPreferenceHelper
import com.example.plantdiseaseai.utils.setUpToolbar
import com.mahmoud.onboardingview.OnBoardingScreen

/**
 * A simple [Fragment] subclass.
 */
class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(requireContext())
        checkFirstLaunchState()
        setUpToolbar(showActionBar = false)
        setupOnBoardingView()
        return binding.root
    }

    private fun checkFirstLaunchState() {
        val state = sharedPreferenceHelper.getFirstLaunchState()
        if (state == false){
            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
        }
    }

    private fun setupOnBoardingView() {
        val screens = arrayListOf<OnBoardingScreen>()
        screens.add(OnBoardingScreen(titleText = "Using your camera",
            subTitleText = "Plant Disease AI enables you to take pictures of your plants under preferred conditions for analysis.",
            screenBGColor = Color.parseColor("#66bb6a"),
            drawableResId = R.drawable.camera_onboard
        ))
        screens.add(OnBoardingScreen(
            titleText = "Uploading an image",
            subTitleText = "Plant Disease AI also allows you upload images already on your device for analysis.",
            drawableResId = R.drawable.upload_onboard,
            screenBGColor = Color.parseColor("#66bb6a")
        ))
        screens.add(OnBoardingScreen(
            titleText = "Alright! Let's get started.",
            subTitleText = "",
            drawableResId = R.drawable.thumbs_up,
            screenBGColor = Color.parseColor("#66bb6a")
        ))

        binding.onboardingView.setScreens(screens)

        binding.onboardingView.onEnd {
            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
            sharedPreferenceHelper.saveFirstLaunchState(false)
        }
    }

}
