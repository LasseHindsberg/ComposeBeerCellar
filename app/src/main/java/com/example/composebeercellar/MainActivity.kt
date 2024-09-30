package com.example.composebeercellar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.composebeercellar.model.BeersViewModel
import com.example.composebeercellar.ui.theme.ComposeBeerCellarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeBeerCellarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: BeersViewModel = viewModel()
    val beers = viewModel.beersFlow.value
    val errorMessage = viewModel.errorMessageFlow.value
    /*
    NavHost(navController = navController, startDestination = NavRoutes.BeerList.route) {
        composable(NavRoutes.BeerList.route) {
            BeerList(
                modifier = modifier,
                beers = beers,
                errorMessage = errorMessage,
                onBeerSelected =
                { beer -> navController.navigate(NavRoutes.BeerDetails)}
            )

     */
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeBeerCellarTheme {
        Greeting("Android")
    }
}