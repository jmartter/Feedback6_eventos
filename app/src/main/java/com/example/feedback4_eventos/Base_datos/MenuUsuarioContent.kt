// MenuUsuarioContent.kt
import androidx.compose.runtime.*
import com.example.feedback4_eventos.AddNovelaScreen
import com.example.feedback4_eventos.Base_datos.Novela
import com.example.feedback4_eventos.Base_datos.UserManager
import com.example.feedback4_eventos.ConfiguracionScreen
import com.example.feedback4_eventos.Menu_Usuario.MenuUsuarioScreen

@Composable
fun MenuUsuarioContent(userName: String) {
    var showAddNovelaScreen by remember { mutableStateOf(false) }
    var showUserNovelasScreen by remember { mutableStateOf(false) }
    var showConfiguracionScreen by remember { mutableStateOf(false) }
    var novelas by remember { mutableStateOf<List<Novela>>(emptyList()) }

    LaunchedEffect(showUserNovelasScreen) {
        if (showUserNovelasScreen) {
            UserManager.getNovelasForUser(userName) { fetchedNovelas ->
                novelas = fetchedNovelas ?: emptyList()
            }
        }
    }

    when {
        showAddNovelaScreen -> {
            AddNovelaScreen(
                onBack = { showAddNovelaScreen = false },
                onAddNovela = { novela ->
                    UserManager.addNovelaToUser(userName, novela)
                    novelas = novelas + novela // Update the novelas list
                    showAddNovelaScreen = false
                }
            )
        }
        showUserNovelasScreen -> {
            ViewNovelasScreen(
                initialNovelas = novelas,
                onBack = { showUserNovelasScreen = false },
                onDeleteNovela = { novela ->
                    UserManager.deleteNovelaFromUser(userName, novela)
                    novelas = novelas - novela
                },
                username = userName
            )
        }
        showConfiguracionScreen -> {
            ConfiguracionScreen(onBack = { showConfiguracionScreen = false })
        }
        else -> {
            MenuUsuarioScreen(
                userName = userName,
                onBack = {},
                onAddNovela = { showAddNovelaScreen = true },
                onViewUserNovelas = { showUserNovelasScreen = true },
                onConfiguracion = { showConfiguracionScreen = true }
            )
        }
    }
}