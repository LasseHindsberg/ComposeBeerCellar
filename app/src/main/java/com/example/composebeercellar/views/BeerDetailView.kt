import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composebeercellar.model.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetailView(
    beer: Beer,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Beer Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Displaying the beer image
            AsyncImage(
                model = beer.pictureUrl,
                contentDescription = "Beer Image",
                modifier = Modifier
                    .size(250.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )

            // Beer Name (Title)
            Text(
                text = beer.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )

            // Brewery (Subtitle)
            Text(
                text = "Brewery: ${beer.brewery}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            // Beer Details (ABV, Style, Volume, etc.)
            BeerDetailRow(label = "Style", value = beer.style)
            BeerDetailRow(label = "ABV", value = "${beer.abv}%")
            BeerDetailRow(label = "Volume", value = "${beer.volume}ml")
            BeerDetailRow(label = "Quantity", value = "${beer.howMany}")
        }
    }
}

@Composable
fun BeerDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun BeerDetailViewPreview() {
    val sampleBeer = Beer(
        id = 1,
        user = "John",
        brewery = "BrewDog",
        name = "Punk IPA",
        style = "IPA",
        abv = 5,
        volume = 330,
        pictureUrl = "https://frugt.dk/img/p/2/4/0/2/6/24026.jpg", // Sample image URL
        howMany = 5
    )

    BeerDetailView(
        beer = sampleBeer,
        onBack = { /* Go back */ }
    )
}
