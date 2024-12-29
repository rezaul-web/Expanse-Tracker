package com.example.expansetracker.screens.income

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expansetracker.screens.addexpanse.AddExpanseViewmodel
import com.example.expansetracker.screens.addexpanse.DatePickerModalInput
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddIncomeScreen(
    modifier: Modifier = Modifier,
    expanseViewmodel: AddIncomeViewmodel = hiltViewModel(),
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                navController.navigate("home") {
                    popUpTo("home") {
                        inclusive=true
                    }
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
            Text(
                text = "Add Income", modifier = Modifier.padding(16.dp),
                fontSize = 32.sp,
                fontFamily = FontFamily.SansSerif
            )
            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
        }

        Spacer(Modifier.height(8.dp))

        ElevatedCard(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(300.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()

            )
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text(text = "Amount") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)


            )

            OutlinedTextField(
                value = date,
                readOnly = true,
                onValueChange = { date = it },
                label = { Text(text = "Date") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        showDatePicker = true

                    }) {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                    }
                }
            )
            if (showDatePicker) {
                DatePickerModalInput(
                    onDateSelected = { millis ->
                        millis?.let {
                            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                            date = sdf.format(Date(it))
                        } ?: run {
                            date = "No date selected"
                        }
                        showDatePicker = false
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
            val context=LocalContext.current
            Button(
                onClick = {
                    if (name.isNotEmpty()&&amount.isNotEmpty()&&date.isNotEmpty()) {
                        expanseViewmodel.getIncome(name, amount.toInt(), date)
                    }
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                }, modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(), enabled = (name.isNotEmpty() && amount.isNotEmpty() && date.isNotEmpty())
            ) {
                Text(text = "Add")
            }

        }


    }

}
