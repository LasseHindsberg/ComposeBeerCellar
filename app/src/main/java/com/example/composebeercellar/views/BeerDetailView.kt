package com.example.composebeercellar.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composebeercellar.model.Beer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerDetailView(
    beer: Beer,
    onBack: () -> Unit,
    onUpdate: (Int, Beer) -> Unit // int = BeerId

) {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(beer.name) }
    var brewery by remember { mutableStateOf(beer.brewery) }
    var style by remember { mutableStateOf(beer.style) }
    var abv by remember { mutableStateOf(beer.abv.toString()) }
    var volume by remember { mutableStateOf(beer.volume.toString()) }
    var amount by remember { mutableStateOf(beer.howMany.toString()) }

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    fun validateInputs(): Boolean {
        return when {
            abv.toDoubleOrNull() == null -> {
                errorMessage = "ABV must be a valid number"
                Log.e("BeerDetailView", "invalid ABV: $abv")
                false
            }

            volume.toDoubleOrNull() == null -> {
                errorMessage = "Volume must be a valid number"
                Log.e("BeerDetailView", "invalid Volume: $volume")
                false
            }

            amount.toIntOrNull() == null -> {
                errorMessage = "Amount must be a valid number"
                Log.e("BeerDetailView", "invalid Amount: $amount")
                false
            }

            name.isBlank() == null -> {
                errorMessage = "Name must not be empty"
                Log.e("BeerDetailView", "invalid Name: $name")
                false
            }

            else -> {
                showError = false
                true
            }
        }
    }
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
                },
                actions = {
                    IconButton(
                        onClick = { isEditing = !isEditing }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "edit",
                        )
                    }
                }
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                ,
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            } // END OF ROW
        Spacer(modifier = Modifier.height(16.dp))
            if (showError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (isEditing) {
                DetailEditableFields(value = name, onValueChange = { name = it }, label = "Name")
                DetailEditableFields(value = brewery, onValueChange = { brewery = it }, label = "Brewery")
                DetailEditableFields(value = style, onValueChange = { style = it }, label = "Style")
                DetailEditableFields(value = abv, onValueChange = { abv = it }, label = "ABV")
                DetailEditableFields(value = volume, onValueChange = { volume = it }, label = "Volume")
                DetailEditableFields(value = amount, onValueChange = { amount = it }, label = "Amount")

                Button(
                    onClick = {
                        if (validateInputs()) {
                            try {
                                val updatedBeer = beer.copy(
                                    name = name,
                                    brewery = brewery,
                                    style = style,
                                    abv = abv.toDouble(),
                                    volume = volume.toDouble(),
                                    howMany = amount.toInt(),
                                    pictureUrl = beer.pictureUrl ?: ""
                                )
                                onUpdate(beer.id, updatedBeer)
                                isEditing = false
                            } catch (e: Exception) {
                                showError = true
                                errorMessage = "Error updating beer: ${e.message}"
                                Log.e("BeerDetailView", "Error updating beer", e)
                            }
                        } else {
                            showError = true
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Save Changes")
                }
            } else {
                Text(
                    text = beer.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = beer.brewery,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                DetailRow(label = "Style", value = beer.style)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 1.dp, color = androidx.compose.ui.graphics.Color.Gray)
                DetailRow(label = "ABV", value = "${beer.abv}%")
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 1.dp, color = androidx.compose.ui.graphics.Color.Gray)
                DetailRow(label = "Volume", value = "${beer.volume}cl")
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 1.dp, color = androidx.compose.ui.graphics.Color.Gray)
                DetailRow(label = "Quantity", value = "${beer.howMany}")
            }
        }
    }

}

// Editable View
@Composable
fun DetailEditableFields(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
}
// Normal View
@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = value,  fontWeight = FontWeight.SemiBold)
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
        abv = 5.0,
        volume = 330.0,
        pictureUrl = "https://frugt.dk/img/p/2/4/0/2/6/24026.jpg", // Sample image URL
        howMany = 5
    )

    BeerDetailView(
        beer = sampleBeer,
        onBack = {},
        onUpdate = { _, _ -> }
    )
}
