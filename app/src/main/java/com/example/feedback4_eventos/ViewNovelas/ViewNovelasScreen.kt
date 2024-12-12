import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.feedback4_eventos.Base_datos.Novela
import com.example.feedback4_eventos.NovelOptionsDialog

@Composable
fun ViewNovelasScreen(initialNovelas: List<Novela>, onBack: () -> Unit, modifier: Modifier = Modifier, onDeleteNovela: (Novela) -> Unit, username: String) {
    var novelas by remember { mutableStateOf(initialNovelas) }
    var selectedNovela by remember { mutableStateOf<Novela?>(null) }
    var showNovelaDetail by remember { mutableStateOf(false) }

    if (showNovelaDetail && selectedNovela != null) {
        ViewNovelaDetailScreen(novela = selectedNovela!!)
    } else {
        Scaffold { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                    NovelList(novelas = novelas, onSelectNovela = { selectedNovela = it })
                }
            }
        }

        selectedNovela?.let { novela ->
            NovelOptionsDialog(
                novela = novela,
                onDismiss = { selectedNovela = null },
                onDelete = {
                    onDeleteNovela(novela)
                    novelas = novelas - novela
                    selectedNovela = null
                },
                onView = { showNovelaDetail = true },
                onToggleFavorite = { success, updatedNovelas ->
                    if (success) {
                        novelas = updatedNovelas ?: novelas
                    }
                },
                username = username
            )
        }
    }
}