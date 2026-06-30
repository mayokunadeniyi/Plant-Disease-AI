# Plant Disease detector and solution system

The project is broken down into multiple steps:

* Building and creating a machine learning model using TensorFlow with Keras
* Deploying the model to an Android application using TFLite
* 
## Machine Learning model using Tensorflow with Keras

We designed algorithms and models to recognize species and diseases in the crop leaves by using CNN (Convolutional Neural Network).

### Dataset
We load a public dataset of 54,305 images of diseased and healthy plant leaves collected under controlled conditions (PlantVillage Dataset). The images cover 14 species of crops, including: apple, blueberry, cherry, grape, orange, peach, pepper, potato, raspberry, soy, squash, strawberry and tomato. It contains images of 17 basic diseases, 4 bacterial diseases, 2 diseases caused by mold (oomycete), 2 viral diseases and 1 disease caused by a mite. 12 crop species also have healthy leaf images that are not visibly affected by disease.

### Model Architecture
The model uses transfer learning with **InceptionV3** as the base feature extractor. On top of the feature extractor, we added:
* Flatten layer
* Dense layer (512 units, ReLU activation)
* Dropout (0.2)
* Final Dense layer (38 units, Softmax activation) for classification across 38 classes.

### Training Results
The model was trained for 10 epochs, achieving:
* **Training Accuracy:** ~91.7%
* **Validation Accuracy:** ~92.0%
* **Loss:** ~0.25

### Performance Visualization
The training process showed consistent improvement in both accuracy and loss across epochs, indicating a well-fitting model without significant overfitting.

### TFLite Conversion
The trained model was exported and converted to TensorFlow Lite format (`plant_disease_model.tflite`) for efficient execution on mobile devices.

## Android Application

The Android app has been modernized to use:
* **Jetpack Compose** for a modern, reactive UI.
* **CameraX** for robust in-app camera functionality.
* **TensorFlow Lite Android Support Library** for on-device inference.

### Features
* **Real-time Analysis:** Capture plant leaf images using the camera for instant disease detection.
* **Gallery Support:** Upload existing images from the device gallery.
* **Informative UI:** Clear display of detected disease and confidence levels.
* **FAQs & About:** Guidance on using the app and information about the project.

## How to Run
1. Open the project in **Android Studio (Koala or newer)**.
2. Ensure you have the TFLite model in `app/src/main/assets/plant_disease_model.tflite`.
3. Sync Gradle and build the project.
4. Run on an Android device or emulator (API 21+).
