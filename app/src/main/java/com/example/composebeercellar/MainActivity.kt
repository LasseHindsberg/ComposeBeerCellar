package com.example.composebeercellar

import com.example.composebeercellar.views.BeerDetailView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composebeercellar.model.Beer
import com.example.composebeercellar.model.BeersViewModel
import com.example.composebeercellar.ui.theme.ComposeBeerCellarTheme
import com.example.composebeercellar.views.BeerListView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeBeerCellarTheme {
                MainScreen()
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

    NavHost(navController = navController, startDestination = NavRoutes.BeerList.route) {
        composable(NavRoutes.BeerList.route) {
            BeerListView(
                modifier = modifier,
                beers = beers,
                errorMessage = errorMessage,
                onBeerSelected =
                { beer -> navController.navigate(NavRoutes.BeerDetails.route + "/${beer.id}") },
                onBeerDeleted = { beer -> viewModel.delete(beer) },
                onAdd = { navController.navigate(NavRoutes.BeerAdd.route) },
                // todo: sorts and filters
            )
        }
        composable(
            NavRoutes.BeerDetails.route + "/{beerId}",
            arguments = listOf(navArgument("beerId") { type = NavType.IntType })
        ) { backstackEntry ->
            val beerId = backstackEntry.arguments?.getInt("beerId")
            val beer = beers.find { it.id == beerId } ?: Beer(
                user = "no user",
                name = "No beer",
                brewery = "no brewery",
                style = "no style",
                abv = 0.0,
                volume = 0.0,
                pictureUrl = "Null",
                howMany = 0
            )
            BeerDetailView(
                beer = beer,
                // todo: onUpdate function
                //onUpdate = {id: Int, beer: Beer -> viewModel.}
                onBack = { navController.popBackStack() })
        }
        /*
        composable(NavRoutes.BeerAdd.route) {
            // todo: beer add screen
            BeerAddView(modifier = modifier,
            addBeer = { beer -> viewModel.add(beer) },
            onBack = { navController.popBackStack() })
            }
             */

    }
}

@Preview(showBackground = true)
@Composable
fun BeerListPreview() {
    ComposeBeerCellarTheme {
        MainScreen()
    }
}
