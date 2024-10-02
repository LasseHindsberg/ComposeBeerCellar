package com.example.composebeercellar.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebeercellar.model.Beer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerListView(
    beers: List<Beer>,
    errorMessage: String,
    modifier: Modifier = Modifier,
    onBeerSelected: (Beer) -> Unit = {}, // For selecting and showing BeerDetailView
    onBeerDeleted: (Beer) -> Unit = {},  // For deleting a beer
    onAdd: () -> Unit = {},              // For adding new beers
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Beer Cellar") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                ) // You can replace this with an appropriate icon
            }
        }
    ) { innerPadding ->
        BeerListPanel(
            beers = beers,
            errorMessage = errorMessage,
            modifier = Modifier.padding(innerPadding),
            onBeerSelected = onBeerSelected,
            onBeerDeleted = onBeerDeleted,
        )
    }
}

@Composable
fun BeerListPanel(
    beers: List<Beer>,
    errorMessage: String,
    modifier: Modifier = Modifier,
    onBeerSelected: (Beer) -> Unit,
    onBeerDeleted: (Beer) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize().padding(8.dp)) {
        if (errorMessage.isNotEmpty()) {
            Text(text = "Problem: $errorMessage", color = MaterialTheme.colorScheme.error)
        }

        // Display the list of beers using LazyColumn
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(beers) { beer ->
                BeerItem(
                    beer = beer,
                    onBeerSelected = { onBeerSelected(beer) },
                    onBeerDeleted = { onBeerDeleted(beer) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerItem(
    beer: Beer,
    modifier: Modifier = Modifier,
    onBeerSelected: (Beer) -> Unit,
    onBeerDeleted: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onBeerSelected(beer) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Beer Name and ABV
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "ABV: ${beer.abv}%",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Edit and Delete Icons
            Row {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onBeerDeleted() }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BeerListViewPreview() {
    val sampleBeers = listOf(
        Beer(id = 1, user = "John", brewery = "BrewDog", name = "Punk IPA", style = "IPA", abv = 5, volume = 330, pictureUrl = "", howMany = 5),
        Beer(id = 2, user = "John", brewery = "Guinness", name = "Guinness Draught", style = "Stout", abv = 4, volume = 440, pictureUrl = "", howMany = 10),
    )

    BeerListView(
        beers = sampleBeers,
        errorMessage = "",
        onBeerSelected = { /* Navigate to BeerDetailView */ },
        onBeerDeleted = { /* Delete beer from list */ },
        onAdd = { /* Navigate to CreateBeerView */ }
    )
}
