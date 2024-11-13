package com.example.composebeercellar.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebeercellar.model.Beer

@Composable
fun BeerAddView(
    modifier: Modifier = Modifier,
    onAddBeer: (Beer) -> Unit,
    onBack: () -> Unit,
    currentUser: String
) {
    var id = 1
    var name by remember { mutableStateOf("") }
    var brewery by remember { mutableStateOf("") }
    var style by remember { mutableStateOf("") }
    var abv by remember { mutableStateOf("") }
    var volume by remember { mutableStateOf("") }
    var pictureUrl by remember { mutableStateOf("") }
    var howMany by remember { mutableStateOf("") }


    Column(modifier = modifier.padding(16.dp)) {
        // Beer Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Beer Name") },
            modifier = Modifier.fillMaxWidth()
        )
        // Brewery
        OutlinedTextField(
            value = brewery,
            onValueChange = { brewery = it },
            label = { Text("Brewery") },
            modifier = Modifier.fillMaxWidth()
        )
        // Style
        OutlinedTextField(
            value = style,
            onValueChange = { style = it },
            label = { Text("Style") },
            modifier = Modifier.fillMaxWidth()
        )
        // ABV
        OutlinedTextField(
            value = abv,
            onValueChange = { abv = it },
            label = { Text("ABV") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        // Volume
        OutlinedTextField(
            value = volume,
            onValueChange = { volume = it },
            label = { Text("Volume (ml)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        // Picture URL
        OutlinedTextField(
            value = pictureUrl,
            onValueChange = { pictureUrl = it },
            label = { Text("Picture URL") },
            modifier = Modifier.fillMaxWidth()
        )
        // How Many
        OutlinedTextField(
            value = howMany,
            onValueChange = { howMany = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Submit Button
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {

                val newBeer = Beer(
                    id = id++,
                    user = currentUser,
                    name = name,
                    brewery = brewery,
                    style = style,
                    abv = abv.toDoubleOrNull() ?: 0.0,
                    volume = volume.toDoubleOrNull() ?: 0.0,
                    pictureUrl = pictureUrl,
                    howMany = howMany.toIntOrNull() ?: 0
                )
                onAddBeer(newBeer)
                onBack()
            }
        ) {
            Text("Add Beer")
        }

        // Back Button
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { onBack() }
        ) {
            Text("Cancel")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BeerAddViewPreview() {
    BeerAddView(
        onAddBeer = { /* Preview action: do nothing */ },
        onBack = { /* Preview action: do nothing */ },
        currentUser = "test_user" // Simulate a logged-in user
    )
}
