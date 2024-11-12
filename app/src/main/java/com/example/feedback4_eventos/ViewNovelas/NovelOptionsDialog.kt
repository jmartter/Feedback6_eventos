// NovelOptionsDialog.kt
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Modifier
import com.example.feedback4_eventos.Base_datos.Novela
import com.example.feedback4_eventos.Base_datos.UserManager

@Composable
fun NovelOptionsDialog(novela: Novela, onDismiss: () -> Unit, onDelete: () -> Unit, onView: () -> Unit, onToggleFavorite: () -> Unit, username: String) {
    var showAddReviewDialog by remember { mutableStateOf(false) }

    if (showAddReviewDialog) {
        AddReviewDialog(
            novela = novela,
            onDismiss = { showAddReviewDialog = false },
            onAddReview = { review ->
                UserManager.addReview(username, novela, review) { success ->
                    if (success) {
                        showAddReviewDialog = false
                    }
                }
            }
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Opciones para ${novela.titulo}") },
            text = {
                Column {
                    TextButton(onClick = onDelete) { Text("Borrar") }
                    TextButton(onClick = onView) { Text("Ver") }
                    TextButton(onClick = {
                        UserManager.toggleFavorite(username, novela) { success ->
                            if (success) {
                                onToggleFavorite()
                            }
                        }
                    }) {
                        Text(if (novela.isFavorite) "Quitar de Favoritos" else "Añadir a Favoritos")
                    }
                    TextButton(onClick = { showAddReviewDialog = true }) { Text("Añadir Reseña") }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) { Text("Cerrar") }
            }
        )
    }
}


@Composable
fun AddReviewDialog(novela: Novela, onDismiss: () -> Unit, onAddReview: (String) -> Unit) {
    var review by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Añadir Reseña a ${novela.titulo}") },
        text = {
            Column {
                OutlinedTextField(
                    value = review,
                    onValueChange = { review = it },
                    label = { Text("Reseña") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onAddReview(review) }) { Text("Añadir") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}