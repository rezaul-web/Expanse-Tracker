package com.example.expansetracker.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemLayout(modifier: Modifier = Modifier, text: String, amount: String, date: String) {
    val t = "\u20B9 $amount"
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = text, fontSize = 24.sp, fontFamily = FontFamily.SansSerif)
            Spacer(Modifier.size(8.dp))
            Text(text = date, fontSize = 14.sp, fontFamily = FontFamily.SansSerif)
        }
        Text(text = t, fontSize = 34.sp, fontFamily = FontFamily.SansSerif)

    }

}