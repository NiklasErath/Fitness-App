plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")}

android {
    namespace = "edu.cc231030.MC.project"
    compileSdk = 34

    defaultConfig {
        applicationId = "edu.cc231030.MC.project"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Keep this as is if compatible
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core libraries
    implementation(libs.androidx.core.ktx) // Core KTX utilities
    implementation(libs.androidx.lifecycle.runtime.ktx) // Lifecycle runtime KTX
    implementation(libs.androidx.activity.compose) // Compose integration with Activity

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom)) // BOM for Compose
    implementation(libs.androidx.ui) // Jetpack Compose UI
    implementation(libs.androidx.ui.graphics) // Compose graphics support
    implementation(libs.androidx.ui.tooling.preview) // Compose preview
    implementation(libs.androidx.material3) // Material 3 UI components

    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.7.4") // Jetpack Compose navigation

    // Lifecycle components for ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // ViewModel KTX
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") // LiveData KTX

    // Kotlin coroutines for Flow and LiveData
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4") // Coroutines core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4") // Coroutines for Android

    // Room for database operations
    implementation(libs.androidx.room.common) // Room core dependencies
    implementation(libs.androidx.room.ktx) // Room KTX extensions

    ksp("androidx.room:room-compiler:2.6.1")

    // Debugging libraries
    debugImplementation(libs.androidx.ui.tooling) // Jetpack Compose tooling for debugging
    debugImplementation(libs.androidx.ui.test.manifest) // Jetpack Compose testing manifest
}
