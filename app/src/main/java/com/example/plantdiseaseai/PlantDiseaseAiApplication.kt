package com.example.plantdiseaseai

import android.app.Application
import timber.log.Timber

/**
 * Created by Mayokun Adeniyi on 10/04/2020.
 */

class PlantDiseaseAiApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}