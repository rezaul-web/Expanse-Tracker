package com.example.expansetracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expansetracker.screens.addexpanse.AddExpanseViewmodel
import com.example.expansetracker.screens.home.ItemLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTransactions(
    modifier: Modifier = Modifier,
    expanseViewmodel: AddExpanseViewmodel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Transactions") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Cyan,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }}
            )

        }
    ) {


        Column(Modifier.padding(it)) {

            val allTransactions by expanseViewmodel.allTransactions.collectAsState()
            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                items(allTransactions) {
                    ItemLayout(text = it.text, amount = it.amount, date = it.date)
                }
            }
        }
    }
}