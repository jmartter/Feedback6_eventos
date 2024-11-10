import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.feedback1_eventos.Base_datos.Novela
import com.example.feedback1_eventos.R

@Composable
fun NovelList(novelas: List<Novela>, onSelectNovela: (Novela) -> Unit, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, modifier = modifier.fillMaxSize()) {
        items(novelas) { novela ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectNovela(novela) }
                    .padding(16.dp)
            ) {
                Text(text = novela.titulo, modifier = Modifier.weight(1f))
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