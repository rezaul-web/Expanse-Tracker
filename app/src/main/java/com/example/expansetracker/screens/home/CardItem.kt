package com.example.expansetracker.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardItem(text: String, amount: Int) {
   val t = "\u20B9 $amount"
    Column {
        Text(
            text = text,
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
        )
        Spacer(Modifier.size(12.dp))
        Text(
            text = t,
            fontFamily = FontFamily.SansSerif,
            fontSize = 34.sp,
        )
    }
}