package com.example.expansetracker.authentication.signUp;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Box;
import androidx.compose.foundation.layout.Column;
import androidx.compose.foundation.layout.Spacer;
import androidx.compose.foundation.layout.fillMaxSize;
import androidx.compose.foundation.layout.fillMaxWidth;
import androidx.compose.foundation.layout.height;
import androidx.compose.foundation.layout.padding;
import androidx.compose.material3.CircularProgressIndicator;
import androidx.compose.material3.OutlinedButton;
import androidx.compose.material3.OutlinedTextField;
import androidx.compose.material3.Text;
import androidx.compose.material3.TextButton;
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.collectAsState;
import androidx.compose.runtime.getValue;
import androidx.compose.runtime.mutableStateOf;
import androidx.compose.runtime.remember;
import androidx.compose.runtime.setValue;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.dp;
import androidx.hilt.navigation.compose.hiltViewModel;
import androidx.navigation.NavController;

@Composable
fun AuthScreen(authViewModel: AuthViewModel = hiltViewModel(), modifier: Modifier = Modifier, navController: NavController) {
    val authState by authViewModel.authState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (authState) {
            is AuthState.Idle -> {
                AuthForm(authViewModel)
            }
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> {
                navController.navigate("home") {
                    popUpTo("auth_screen") { inclusive = true }
                    launchSingleTop = true
                }
            }
            is AuthState.Error -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = (authState as AuthState.Error).error)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AuthForm(authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (isSignUp) "Sign Up" else "Sign In")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                if (isSignUp) {
                    authViewModel.signUp(email, password)
                } else {
                    authViewModel.login(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (isSignUp) "Sign Up" else "Sign In")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { isSignUp = !isSignUp },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (isSignUp) "Already have an account? Sign In" else "Don't have an account? Sign Up")
        }
    }
}
