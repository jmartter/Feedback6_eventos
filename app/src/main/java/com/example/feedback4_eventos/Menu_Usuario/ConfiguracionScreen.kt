// ConfiguracionScreen.kt
package com.example.feedback4_eventos

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.feedback4_eventos.Tema.ThemeManager

@Composable
fun ConfiguracionScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val isDarkTheme by ThemeManager.isDarkTheme.collectAsState()

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = {
                            ThemeManager.setDarkTheme(context, isDarkTheme)
                            onBack()
                        },
                        modifier = Modifier.align(Alignment.Start).padding(16.dp)
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        ThemeManager.setDarkTheme(context, !isDarkTheme)
                    }) {
                        Text(text = if (isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode")
                    }
                }
            }
        }
    }
}