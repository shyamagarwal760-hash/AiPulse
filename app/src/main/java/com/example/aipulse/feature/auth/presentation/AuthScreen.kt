package com.example.aipulse.feature.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aipulse.R

enum class AuthMode {
    Login,
    SignUp,
}

@Composable
fun AuthScreen(
    mode: AuthMode,
    onModeChange: (AuthMode) -> Unit,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFF5F8FF), Color.White, Color(0xFFFFF7F3)),
                ),
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.ai_pulse_logo),
                contentDescription = "AI Pulse logo",
                modifier = Modifier.size(112.dp),
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = if (mode == AuthMode.Login) "Welcome back" else "Create your pulse",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF101B42),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (mode == AuthMode.Login) {
                    "Your daily signal on what matters."
                } else {
                    "Stay informed without the noise."
                },
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF5E6680),
            )
            Spacer(modifier = Modifier.height(28.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                color = Color.White,
                shadowElevation = 8.dp,
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    if (mode == AuthMode.SignUp) {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Full name") },
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                        )
                    }
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email address") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(16.dp),
                    )
                    if (mode == AuthMode.Login) {
                        TextButton(
                            onClick = {},
                            modifier = Modifier.align(Alignment.End),
                        ) {
                            Text("Forgot password?")
                        }
                    }
                    Button(
                        onClick = onContinue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF132D70),
                        ),
                    ) {
                        Text(
                            text = if (mode == AuthMode.Login) "Enter AI Pulse" else "Start reading",
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(22.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (mode == AuthMode.Login) "New to AI Pulse?" else "Already have an account?",
                    color = Color(0xFF5E6680),
                )
                TextButton(onClick = {
                    onModeChange(if (mode == AuthMode.Login) AuthMode.SignUp else AuthMode.Login)
                }) {
                    Text(
                        text = if (mode == AuthMode.Login) "Create account" else "Log in",
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
