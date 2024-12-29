package com.example.expansetracker.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toLocalDate(): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy")
    return try {
        LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        null // Return null if the date is not valid
    }
}