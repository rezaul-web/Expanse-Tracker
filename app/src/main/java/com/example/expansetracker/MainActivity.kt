package com.example.expansetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expansetracker.authentication.signUp.AuthScreen
import com.example.expansetracker.screens.AllTransactions
import com.example.expansetracker.screens.addexpanse.AddExpanseScreen
import com.example.expansetracker.screens.graph.ExpanseGraph
import com.example.expansetracker.screens.home.HomeScreen
import com.example.expansetracker.screens.home.MinimalDropFloating
import com.example.expansetracker.screens.income.AddIncomeScreen
import com.example.expansetracker.ui.theme.ExpanseTrackerTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val auth = Firebase.auth
            val currentUser=auth.currentUser
            val startDestination= if (currentUser==null) "auth_screen" else "home"
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
                    NavHost(navController = navController, startDestination =startDestination) {
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
                      composable(route = "auth_screen") {
                          AuthScreen(navController = navController)
                      }
                    }
                }
            }
        }
    }
}




