package com.example.expansetracker

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExpanseApp:Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
         FirebaseApp.initializeApp(this)

    }
}