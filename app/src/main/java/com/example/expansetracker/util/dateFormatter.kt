package com.example.expansetracker.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Extension function to parse String to Date with format defined inline
fun String.dateFormatter(): Date? {
    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) // Define format within the function
    return try {
        format.parse(this) // Parse the string into a Date object
    } catch (e: Exception) {
        null // Return null if the string cannot be parsed
    }
}
