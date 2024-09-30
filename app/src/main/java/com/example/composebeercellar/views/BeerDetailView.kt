package com.example.composebeercellar.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.composebeercellar.model.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetailView(
    beer: Beer,
    modifier: Modifier = Modifier,
    onUpdate: (Int, Beer) -> Unit = {id: Int, data: Beer -> },
    onNavigateBack: () -> Unit = {}
) {
    var title by remember { MutableStateOf(beer.name)}
    // TODO: add all variables of beer to a "by remember"

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text("Beer Details") })
        }) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            // TODO: Layout for landscape
            // TODO: add detailed view for each part of the beer


        }
    }
}