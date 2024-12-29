package com.example.expansetracker

import android.graphics.drawable.Icon
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expansetracker.screens.income.AddIncomeScreen
import com.example.expansetracker.screens.AllTransactions
import com.example.expansetracker.screens.addexpanse.AddExpanseScreen
import com.example.expansetracker.screens.graph.ExpanseGraph
import com.example.expansetracker.screens.home.HomeScreen
import com.example.expansetracker.screens.home.MinimalDropFloating
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
                        }, Modifier.size(60.dp)) {
                            MinimalDropFloating(onClickItem1 = {
                                navController.navigate("addIncome")
                            }, onClickItem2 = {
                                navController.navigate("addExpanse")
                            })
                        }
                    },
                    bottomBar = {

                            BottomAppBar(
                                actions = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {

                                        IconButton(onClick = {
                                            if (navController.currentDestination?.route != "home")
                                                navController.navigate("home") {
                                                    popUpTo("home") {
                                                        inclusive = true
                                                    }
                                                }
                                        }) {
                                            Icon(
                                                Icons.Default.Home,
                                                contentDescription = null,
                                                Modifier.size(80.dp)
                                            )
                                        }
                                        IconButton(onClick = {
                                            navController.navigate("expanse_graph")
                                        }) {
                                            Icon(
                                                painter = painterResource(R.drawable.stats),
                                                contentDescription = null,
                                                modifier = Modifier.size(100.dp),
                                            )
                                        }


                                    }
                                },

                                )



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
                        composable(route = "addIncome") {
                            AddIncomeScreen(
                                navController = navController
                            )
                        }
                        composable(route = "all_transaction_screen") {
                            AllTransactions(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )

                        }
                        composable(route = "expanse_graph") {
                            ExpanseGraph()
                        }
                    }
                }
            }
        }
    }
}

data class BottomBarItem(val name: String, val icon: ImageVector)

//@Composable
////fun BottomAppBar(
//    modifier: Modifier = Modifier,
//    items: List<BottomBarItem>, // Pass the list of items to display
//    onItemClick: (BottomBarItem) -> Unit
//) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth() // Fill the width
//            .background(Color.Cyan), // Padding on both sides for spacing
//        horizontalArrangement = Arrangement.SpaceEvenly, // Distribute the items evenly
//        verticalAlignment = Alignment.CenterVertically // Vertically center the items
//    ) {
//        items.forEach { item ->
//            IconButton(onClick = { onItemClick(item) }) {
//                Icon(item.icon, contentDescription = item.name,modifier.size(80.dp))
//            }
//        }
//    }
//}




