package com.example.expansetracker.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.expansetracker.R

@Composable
fun MinimalDropdownMenu(
    onClickItem1: () -> Unit = {},
    onClickItem2: () -> Unit = {},
    item1: String = "",
    item2: String = ""
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                painter = painterResource(R.drawable.dots_menu),
                contentDescription = "More options"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = item1) },
                onClick = {
                    onClickItem1()
                }
            )
            DropdownMenuItem(
                text = { Text(text = item2) },
                onClick = {
                    onClickItem2()
                }
            )
        }
    }
}