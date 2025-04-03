plugins {
    // Keep existing plugins
    id("com.android.application") // Assuming this was aliased before
    id("org.jetbrains.kotlin.android") // Assuming this was aliased before
    // Compose plugin likely doesn't have a standard non-catalog ID, keep alias if it works
    // If not, you might need to find its direct ID or add it via classpath in the project build.gradle
    alias(libs.plugins.kotlin.compose) // Keeping this as its direct ID isn't standard

    // --- ADDED PLUGINS ---
    id("com.google.dagger.hilt.android") version "2.51" // Hilt plugin - Specify version here
    id("org.jetbrains.kotlin.kapt") // Kapt plugin for annotation processing
    // --------------------
}

android {
    namespace = "com.example.myexpansestracker"
    compileSdk = 35 // Ensure your compileSdk is appropriate for the libraries

    defaultConfig {
        applicationId = "com.example.myexpansestracker"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        // Keep Java 11 for now as requested, but be aware latest AGP/libraries might prefer/require 17+
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11" // Keep Java 11 for now
    }
    buildFeatures {
        compose = true
    }
    // --- ADDED FOR KAPT ---
    kapt {
        correctErrorTypes = true // Recommended Kapt setting
    }
    // ----------------------
}

dependencies {

    // --- VERSIONS (Define here for clarity without catalog) ---
    val roomVersion = "2.6.1"
    val navigationVersion = "2.7.7" // Updated to latest stable
    val hiltVersion = "2.51" // Match plugin version
    val hiltNavigationComposeVersion = "1.2.0" // Updated to latest stable

    // --- HILT Dependencies ---
    implementation("com.google.dagger:hilt-android:${hiltVersion}")
    kapt("com.google.dagger:hilt-compiler:${hiltVersion}")
    // For Hilt Navigation Compose integration
    implementation("androidx.hilt:hilt-navigation-compose:${hiltNavigationComposeVersion}")
    // -------------------------

    // --- Room Dependencies ---
    implementation("androidx.room:room-runtime:${roomVersion}")
    kapt("androidx.room:room-compiler:${roomVersion}")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:${roomVersion}")
    // Optional - Test helpers
    // testImplementation("androidx.room:room-testing:$roomVersion")
    // androidTestImplementation("androidx.room:room-testing:$roomVersion")
    // -----------------------

    // --- Navigation Compose Dependency ---
    implementation("androidx.navigation:navigation-compose:${navigationVersion}")
    // ---------------------------------

    // --- Keep Existing Dependencies (using libs alias as they were before) ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // ---------------------------------------------------------------------
}