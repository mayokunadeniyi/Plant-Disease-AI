package com.example.plantdiseaseai.utils

import android.view.View

/**
 * Created by Mayokun Adeniyi on 21/04/2020.
 */

// Legacy helper for old View-based layouts
inline fun <T: View> T.showIf(condition: (T) -> Boolean){
    visibility = if (condition(this)){
        View.VISIBLE
    }else{
        View.GONE
    }
}
