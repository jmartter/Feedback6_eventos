package com.example.feedback4_eventos

import NovelList
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.feedback4_eventos.Base_datos.Novela
import com.example.feedback4_eventos.Base_datos.UserManager
import com.example.feedback4_eventos.Inicio.LoginActivity
import kotlinx.coroutines.delay

@Composable
fun MenuUsuarioScreen(
    userName: String,
    onBack: () -> Unit,
    onAddNovela: () -> Unit,
    onViewUserNovelas: () -> Unit,
    onConfiguracion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var novelas by remember { mutableStateOf<List<Novela>>(emptyList()) }

    // Periodically refresh the list of novelas
    LaunchedEffect(Unit) {
        while (true) {
            UserManager.getNovelasForUser(userName) { fetchedNovelas ->
                novelas = fetchedNovelas ?: emptyList()
            }
            delay(10000)
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            IconButton(
                onClick = {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp), // Adjust padding to place the text below the back button
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Bienvenido $userName", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onAddNovela,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Añadir Novela")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onConfiguracion,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Settings, contentDescription = "Configuración")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Configuración")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Lista de Novelas", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
                NovelList(novelas = novelas, onSelectNovela = { /* Handle novela selection */ })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuUsuarioScreenPreview() {
    MenuUsuarioScreen(userName = "User", onBack = {}, onAddNovela = {}, onViewUserNovelas = {}, onConfiguracion = {})
}