// MenuUsuarioActivity.kt
package com.example.feedback4_eventos.Inicio

import MenuUsuarioContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.feedback4_eventos.Tema.ThemeManager

class MenuUsuarioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userName = intent.getStringExtra("username") ?: "User"
        ThemeManager.loadTheme(this)
        setContent {
            val context = LocalContext.current
            val isDarkTheme by ThemeManager.isDarkTheme.collectAsState()

            MaterialTheme(
                colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
            ) {
                MenuUsuarioContent(userName)
            }
        }
    }
}