package com.example.plantdiseaseai.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantdiseaseai.ui.screens.*
import com.example.plantdiseaseai.ui.theme.PlantDiseaseAITheme
import com.example.plantdiseaseai.utils.Classifier

class MainActivity : ComponentActivity() {
    private lateinit var classifier: Classifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        classifier = Classifier(assets, "plant_disease_model.tflite", "plant_labels.txt", 299)

        setContent {
            PlantDiseaseAITheme {
                val navController = rememberNavController()
                var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            onNavigate = { route ->
                                navController.navigate(route)
                            }
                        )
                    }
                    composable("camera") {
                        CameraScreen(
                            onImageCaptured = { uri ->
                                capturedImageUri = uri
                                navController.navigate("result")
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("gallery") {
                        GalleryScreen(
                            onImageSelected = { uri ->
                                capturedImageUri = uri
                                navController.navigate("result")
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("result") {
                        capturedImageUri?.let { uri ->
                            ResultScreen(
                                imageUri = uri,
                                classifier = classifier,
                                onBack = { navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }},
                                onRetry = { navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }}
                            )
                        }
                    }
                    composable("faq") {
                        FaqScreen(onBack = { navController.popBackStack() })
                    }
                    composable("about") {
                        AboutScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
