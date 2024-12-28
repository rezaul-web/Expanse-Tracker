package com.example.expansetracker.screens.home

import androidx.compose.runtime.Composable
import com.example.expansetracker.data.model.TransactionItem

@Composable
 fun transactionItems(): List<TransactionItem> {
    val transactionItems = listOf(
        TransactionItem(text = "Netflix", amount = 1000, date = "20/12/2024"),
        TransactionItem(text = "Netflix", amount = 1000, date = "20/12/2024"),
        TransactionItem(text = "Netflix", amount = 1000, date = "20/12/2024"),
        TransactionItem(text = "Netflix", amount = 1000, date = "20/12/2024"),
        TransactionItem(text = "Netflix", amount = 1000, date = "20/12/2024"),
        TransactionItem(text = "Netflix", amount = 1000, date = "20/12/2024"),
        TransactionItem(text = "Netflix", amount = 1000, date = "20/12/2024"),
        TransactionItem(text = "Netflix", amount = 1000, date = "20/12/2024"),
        TransactionItem(text = "Netflix", amount = 1000, date = "20/12/2024"),

        )
    return transactionItems
}