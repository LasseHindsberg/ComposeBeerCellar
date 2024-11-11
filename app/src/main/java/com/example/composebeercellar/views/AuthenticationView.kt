package com.example.composebeercellar.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationView(
    user: FirebaseUser? = null,
    message: String = "",
    signIn: (email: String, password: String) -> Unit = { _, _ -> },
    register: (email: String, password: String) -> Unit = { _, _ -> },
    navigateToNextScreen: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    fun isValidInput(): Boolean {
        errorMessage = when {
            email.isEmpty() -> "Email cannot be empty"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            password.isEmpty() -> "Password cannot be empty"
            password.length < 6 -> "Password must be at least 6 characters long"
            else -> ""
        }
        return errorMessage.isEmpty()
    }

    if (user != null) {
        navigateToNextScreen()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Beer Cellar")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Login or register")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (isValidInput()) {

                signIn(email, password)
            }
        }) {
            Text("Sign In")
        }


        Button(onClick = {
            if (isValidInput()) {
                register(email, password)
            }
        }) {
            Text("Register")
        }

        if (errorMessage.isNotEmpty() || message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorMessage.ifEmpty { message },
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationPreview() {
    AuthenticationView()
}