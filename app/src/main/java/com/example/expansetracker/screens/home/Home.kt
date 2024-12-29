package com.example.expansetracker.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expansetracker.budget.BudgetViewModel
import com.example.expansetracker.data.model.IncomeItem
import com.example.expansetracker.data.model.TransactionItem
import com.example.expansetracker.screens.addexpanse.AddExpanseViewmodel
import com.example.expansetracker.screens.income.AddIncomeViewmodel
import com.example.expansetracker.util.DeleteAlertDialog
import com.example.expansetracker.util.dateFormatter
import com.example.expansetracker.util.toLocalDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    expanseViewmodel: AddExpanseViewmodel = hiltViewModel(),
    incomeViewmodel: AddIncomeViewmodel = hiltViewModel(),
    navController: NavController,
    dataStoreViewmodel: BudgetViewModel = hiltViewModel()
) {
    val recentTransaction by expanseViewmodel.recentTransactions.collectAsState()
    val incomeTransactions by incomeViewmodel.allIncomes.collectAsState()
    val totalExpanse by expanseViewmodel.totalExpanse.collectAsState()
    val totalIncome by incomeViewmodel.totalIncome.collectAsState()
    val context = LocalContext.current
    val totalAmount by dataStoreViewmodel.totalBudget.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFedede9))
    ) {
        Spacer(Modifier.height(80.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Good Morning ",
                    fontSize = 34.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(start = 20.dp)

                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Rezaul",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(start = 20.dp)

                )
            }

            IconButton(onClick = {}) {
                Icon(Icons.Default.Notifications, contentDescription = null)
            }

        }
        ElevatedCard(
            modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFe3d5ca))
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardItem(text = "Amount", amount = totalAmount+totalIncome - totalExpanse)

                MinimalDropdownMenu(onClickItem1 = {
                    showBottomSheet = true
                    dataStoreViewmodel.updateTotalBudget(totalAmount + 1000)

                }, onClickItem2 = {
                    showDeleteDialog = true
                }, item1 = "Add 1000", item2 = "Clear Dashboard")

            }
            if (showDeleteDialog) {
                DeleteAlertDialog(onDismiss = { showDeleteDialog = false }, onConfirm = {
                    dataStoreViewmodel.updateTotalBudget(0)
                    expanseViewmodel.deleteAll()
                    incomeViewmodel.deleteAll()
                    Toast.makeText(context, "Cleared", Toast.LENGTH_SHORT).show()
                    showDeleteDialog = false
                }, title = "Clear Dashboard", content = "You are about to clear everything")
            }
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                CardItem(text = "Income", amount = totalIncome)
                CardItem(text = "Expanse", amount = totalExpanse)
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recent Transaction",
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif
            )
            TextButton(onClick = {
                navController.navigate("all_transaction_screen")
            }) {
                Text(text = "See All")
            }

        }





        val combinedTransactions = (recentTransaction.map { Transaction.Expanse(it) } +
                incomeTransactions.map { Transaction.Income(it) })
            .sortedByDescending { transaction ->
                // Safely get the date as Date and handle invalid cases
                when (transaction) {
                    is Transaction.Expanse -> transaction.transactionItem.date.dateFormatter()
                    is Transaction.Income -> transaction.incomeItem.date.dateFormatter()
                }
            }




        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            items(combinedTransactions) { transactions ->
                when (transactions) {
                    is Transaction.Expanse -> {
                        ItemLayout(
                            text = transactions.transactionItem.text,
                             amount = "- ${transactions.transactionItem.amount}",
                            date = transactions.transactionItem.date
                        )
                    }

                    is Transaction.Income -> {
                        val t=TransactionItem(
                            text = transactions.incomeItem.text,
                            amount = transactions.incomeItem.amount,
                            date = transactions.incomeItem.date
                        )
                        ItemLayout(
                            text = " ${t.text}",
                            amount = "+ ${t.amount}",
                            date =t.date
                        )
                    }
                }
            }


        }

    }
}

sealed class Transaction {
    data class Expanse(val transactionItem: TransactionItem) : Transaction()
    data class Income(val incomeItem: IncomeItem) : Transaction()
}



