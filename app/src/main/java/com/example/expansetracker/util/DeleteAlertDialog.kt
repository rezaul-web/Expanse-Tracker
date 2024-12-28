package com.example.expansetracker.util

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    content: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = content, style = MaterialTheme.typography.bodyMedium)
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        }
    )
}
