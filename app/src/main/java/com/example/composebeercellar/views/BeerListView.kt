package com.example.composebeercellar.views

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebeercellar.model.Beer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerListView(
    beers: List<Beer>,
    errorMessage: String,
    modifier: Modifier = Modifier,
    onBeerSelected: (Beer) -> Unit = {},
    onBeerDeleted: (Beer) -> Unit = {},
    onAdd: () -> Unit = {},
    onLogout: () -> Unit = {},
    sortByName: (up: Boolean) -> Unit = {},
    sortByABV: (up: Boolean) -> Unit = {},
    filterByName: (String) -> Unit = {}

) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Beer Cellar") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp, // Choose an appropriate icon
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerPadding ->
        BeerListPanel(
            beers = beers,
            errorMessage = errorMessage,
            modifier = Modifier.padding(innerPadding),
            onBeerSelected = onBeerSelected,
            onBeerDeleted = onBeerDeleted,
            sortByName = sortByName,
            sortByABV = sortByABV,
            onFilterByTitle = filterByName
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
    sortByName: (up: Boolean) -> Unit,
    sortByABV: (up: Boolean) -> Unit,
    onFilterByTitle: (String) -> Unit,
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)) {
        if (errorMessage.isNotEmpty()) {
            Text(text = "Problem: $errorMessage", color = MaterialTheme.colorScheme.error)
        }
        val nameUp = "Name \u2191"
        val nameDown = "Name \u2193"
        val abvUp = "ABV \u2191"
        val abvDown = "ABV \u2193"

        var sortNameAscending by remember { mutableStateOf(true) }
        var sortAbvAscending by remember { mutableStateOf(true) }
        var nameFragment by remember { mutableStateOf("") }

        // Filter Field and Sorting.
        Row {
            OutlinedTextField(
                value = nameFragment,
                onValueChange = { nameFragment = it },
                label = { Text("Filter by name") },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { onFilterByTitle(nameFragment) }) {
                Text("Filter")
            }
        }

        Row {
            OutlinedButton(onClick = {
                sortByName(sortNameAscending)
                sortNameAscending = !sortNameAscending
            }) {
                Text(text = if (sortNameAscending) nameDown else nameUp)
            }
            OutlinedButton(onClick = {
                sortByABV(sortAbvAscending)
                sortAbvAscending = !sortAbvAscending
            }) {
                Text(text = if (sortAbvAscending) abvDown else abvUp)
            }
        }
        val orientation = LocalConfiguration.current.orientation
        val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
        ) {
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
        Beer(
            id = 1,
            user = "John",
            brewery = "BrewDog",
            name = "Punk IPA",
            style = "IPA",
            abv = 5.0,
            volume = 330.0,
            pictureUrl = "",
            howMany = 5
        ),
        Beer(
            id = 2,
            user = "John",
            brewery = "Guinness",
            name = "Guinness Draught",
            style = "Stout",
            abv = 4.0,
            volume = 440.0,
            pictureUrl = "",
            howMany = 10
        ),
    )

    BeerListView(
        beers = sampleBeers,
        errorMessage = "",
        onBeerSelected = { /* Navigate to BeerDetailView */ },
        onBeerDeleted = { /* Delete beer from list */ }
    )
}
