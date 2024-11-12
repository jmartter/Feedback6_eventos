// LoginActivity.kt
package com.example.feedback4_eventos.Inicio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.feedback1_eventos.LoginScreen
import com.example.feedback1_eventos.RegisterScreen
import com.example.feedback4_eventos.Base_datos.UserManager

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showRegisterScreen by remember { mutableStateOf(false) }

            if (showRegisterScreen) {
                RegisterScreen(
                    onRegister = { username: String, password: String, showMessage ->
                        UserManager.registerUser(username, password) { success, message ->
                            showMessage(message)
                            if (success) {
                                saveUserCredentials(username, password)
                                val intent = Intent(this, MenuUsuarioActivity::class.java)
                                intent.putExtra("username", username)
                                startActivity(intent)
                                finish()
                            }
                        }
                    },
                    onBack = { showRegisterScreen = false }
                )
            } else {
                LoginScreen(
                    onLogin = { username: String, password: String, showMessage ->
                        UserManager.getUser(username, password) { user ->
                            if (user != null) {
                                saveUserCredentials(username, password)
                                val intent = Intent(this, MenuUsuarioActivity::class.java)
                                intent.putExtra("username", user.username)
                                startActivity(intent)
                                finish()
                            } else {
                                showMessage("Invalid username or password")
                            }
                        }
                    },
                    onNavigateToRegister = { showRegisterScreen = true }
                )
            }
        }
    }

    private fun saveUserCredentials(username: String, password: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("username", username)
            putString("password", password)
            apply()
        }
    }
}