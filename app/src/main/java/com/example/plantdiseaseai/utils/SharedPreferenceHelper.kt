package com.example.plantdiseaseai.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

/**
 * Created by Mayokun Adeniyi on 2020-01-28.
 */

class SharedPreferenceHelper {

    companion object{

        private const val FIRST_LAUNCH = "First Launch"
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferenceHelper? = null

        fun getInstance(context: Context): SharedPreferenceHelper{
            synchronized(this){
                var _instance = instance
                if (_instance == null){
                    _instance = SharedPreferenceHelper()
                    prefs = PreferenceManager.getDefaultSharedPreferences(context)
                    instance = _instance
                }
                return _instance
            }
        }
    }

    fun saveFirstLaunchState(state: Boolean){
        prefs?.edit(commit = true){
            putBoolean(FIRST_LAUNCH,state)
        }
    }

    fun getFirstLaunchState() = prefs?.getBoolean(FIRST_LAUNCH,true)


}