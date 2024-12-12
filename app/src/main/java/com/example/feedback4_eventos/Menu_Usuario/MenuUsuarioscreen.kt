package com.example.feedback4_eventos.Menu_Usuario

import ViewNovelaDetailScreen
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.feedback4_eventos.Base_datos.Novela
import com.example.feedback4_eventos.Base_datos.UserManager
import com.example.feedback4_eventos.Inicio.LoginActivity
import com.example.feedback4_eventos.NovelOptionsDialog
import com.example.feedback4_eventos.R
import com.example.feedback4_eventos.RandomLocationUpdater
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

@OptIn(ExperimentalMaterial3Api::class)
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
    var selectedNovela by remember { mutableStateOf<Novela?>(null) }
    var showNovelaDetail by remember { mutableStateOf(false) }
    var showNovelOptionsDialog by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf<Location?>(null) }
    var showLocationDialog by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                getLocation(context) { loc -> location = loc }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            getLocation(context) { loc -> location = loc }
        }
    }

    DisposableEffect(Unit) {
        val randomLocationUpdater = RandomLocationUpdater(context) { loc ->
            location = loc
        }
        randomLocationUpdater.start()
        onDispose {
            randomLocationUpdater.stop()
        }
    }

    // Periodically refresh the list of novelas
    LaunchedEffect(Unit) {
        while (true) {
            UserManager.getNovelasForUser(userName) { fetchedNovelas ->
                novelas = fetchedNovelas ?: emptyList()
            }
            delay(100)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menu Usuario") },
                actions = {
                    IconButton(onClick = { showLocationDialog = true }) {
                        Icon(Icons.Filled.LocationOn, contentDescription = "Show Location")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Back Button
            IconButton(
                onClick = {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }

            // Main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp), // Adjust padding to place the text below the back button
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Bienvenido $userName", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

                // Add Novela Button
                Button(
                    onClick = onAddNovela,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp) // Adjust space between buttons
                ) {
                    Text("Añadir Novela")
                }

                // Configuración Button
                Button(
                    onClick = onConfiguracion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp) // Adjust space between buttons
                ) {
                    Icon(Icons.Filled.Settings, contentDescription = "Configuración")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Configuración")
                }

                // Novela List Section
                Text(text = "Lista de Novelas", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))

                // Box for novelas list with fixed size and scrollbar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Fixed height for the novelas list
                        .padding(16.dp)
                        .border(1.dp, Color.Gray) // Border to make it visually distinct
                ) {
                    val state = rememberLazyListState()
                    LazyColumn(
                        state = state,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(novelas) { novela ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedNovela = novela
                                        showNovelOptionsDialog = true // Ensure dialog shows when a novela is clicked
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = novela.titulo,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                // Display star if novela is favorite
                                if (novela.isFavorite) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.estrella),
                                        contentDescription = "Favorite",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Box for novela details
                if (showNovelaDetail && selectedNovela != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .border(1.dp, Color.Gray) // Border to make it visually distinct
                            .verticalScroll(rememberScrollState()) // Habilita desplazamiento vertical
                            .heightIn(max = 300.dp) // Limita la altura máxima
                    ) {
                        ViewNovelaDetailScreen(novela = selectedNovela!!)
                    }
                }
            }
        }
    }

    // Mostrar el diálogo de opciones cuando se selecciona una novela
    selectedNovela?.let { novela ->
        if (showNovelOptionsDialog) {
            NovelOptionsDialog(
                novela = novela,
                onDismiss = { showNovelOptionsDialog = false },
                onDelete = {
                    UserManager.deleteNovelaFromUser(userName, novela)
                    novelas = novelas - novela
                    showNovelOptionsDialog = false
                    selectedNovela = null
                },
                onView = {
                    showNovelaDetail = true // Mantener el detalle visible
                    showNovelOptionsDialog = false // Cerrar el diálogo
                },
                onToggleFavorite = { success, updatedNovelas ->
                    if (success) {
                        novelas = updatedNovelas ?: novelas
                    }
                },
                username = userName
            )
        }
    }

    // Mostrar el diálogo de selección de ubicación
    if (showLocationDialog) {
        AlertDialog(
            onDismissRequest = { showLocationDialog = false },
            title = { Text("Seleccionar Ubicación") },
            text = {
                Column {
                    TextButton(onClick = {
                        // Lógica para mostrar ubicaciones de novelas
                        showLocationDialog = false
                    }) {
                        Text("Ubicaciones de Novelas")
                    }
                    TextButton(onClick = {
                        // Lógica para mostrar mi ubicación
                        location?.let {
                            val latitude = it.latitude
                            val longitude = it.longitude
                            val mapsUrl = "https://www.google.com/maps?q=$latitude,$longitude"
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = android.net.Uri.parse(mapsUrl)
                            }
                            context.startActivity(intent)
                        }
                        showLocationDialog = false
                    }) {
                        Text("Mi Ubicación")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLocationDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

private fun getLocation(context: Context, callback: (Location) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    try {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let { callback(it) }
            }
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}

@Composable
@Preview(showBackground = true)
fun MenuUsuarioScreenPreview() {
    MenuUsuarioScreen(userName = "User", onBack = {}, onAddNovela = {}, onViewUserNovelas = {}, onConfiguracion = {})
}