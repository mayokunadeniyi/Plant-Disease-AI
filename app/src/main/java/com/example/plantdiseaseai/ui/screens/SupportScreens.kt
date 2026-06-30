package com.example.plantdiseaseai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(onBack: () -> Unit) {
    val faqs = listOf(
        "What is Plant Disease AI?" to "It is an AI-powered app that helps identify plant diseases from images.",
        "How do I use the camera?" to "Tap the Camera card on the home screen, point at a plant leaf, and press capture.",
        "Is it accurate?" to "The app uses a TFLite model trained on common crop diseases. Accuracy depends on image quality and lighting."
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FAQs") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            faqs.forEach { (q, a) ->
                Text(text = q, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = a, modifier = Modifier.padding(bottom = 24.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Plant Disease AI",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Version 2.0 (Modernized)")
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "This app uses TensorFlow Lite to perform on-device plant disease classification. It supports both live camera capture and gallery uploads.")
        }
    }
}
