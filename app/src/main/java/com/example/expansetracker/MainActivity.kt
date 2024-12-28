package com.example.expansetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expansetracker.screens.AllTransactions
import com.example.expansetracker.screens.addexpanse.AddExpanseScreen
import com.example.expansetracker.screens.home.HomeScreen
import com.example.expansetracker.ui.theme.ExpanseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpanseTrackerTheme {
                val navController = rememberNavController()


                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navController.navigate("addExpanse")
                        }) {
                            MinimalDropFloating(onClickItem1 = {
                                navController.navigate("addExpanse")
                            }, onClickItem2 = {
                                navController.navigate("addExpanse")
                            })
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home") {
                        composable(route = "home") {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable(route = "addExpanse") {
                            AddExpanseScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable(route = "all_transaction_screen") {
                            AllTransactions(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MinimalDropFloating(
    onClickItem1: () -> Unit = {},
    onClickItem2: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                Icons.Default.Add,
                contentDescription = "More options"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Add Income") }, // Label for the first item
                leadingIcon = { Icon(Icons.Default.Add, contentDescription = "Add Icon") },
                onClick = {
                    onClickItem1()
                    expanded = false // Close menu after click
                }
            )
            DropdownMenuItem(
                text = { Text("Add Expanse") }, // Label for the second item
                leadingIcon = {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Down Arrow Icon"
                    )
                },
                onClick = {
                    onClickItem2()
                    expanded = false // Close menu after click
                }
            )
        }
    }
}

