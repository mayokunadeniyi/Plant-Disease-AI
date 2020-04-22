package com.example.plantdiseaseai.utils

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.plantdiseaseai.R
import com.example.plantdiseaseai.ui.MainActivity

/**
 * Created by Mayokun Adeniyi on 21/04/2020.
 */


fun Fragment.setUpToolbar(
    showActionBar: Boolean,
    showBackButton: Boolean = true
) {
    (requireActivity() as MainActivity).apply {
        if (supportActionBar != null) {
            if (!showActionBar) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(showBackButton)
                    show()
                }
            }
        }
    }
}

inline fun <T: View> T.showIf(condition: (T) -> Boolean){
    visibility = if (condition(this)){
        View.VISIBLE
    }else{
        View.GONE
    }
}