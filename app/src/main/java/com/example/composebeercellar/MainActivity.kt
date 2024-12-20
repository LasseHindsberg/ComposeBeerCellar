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
import com.example.composebeercellar.model.AuthenticationViewModel
import com.example.composebeercellar.model.Beer
import com.example.composebeercellar.model.BeersViewModel
import com.example.composebeercellar.ui.theme.ComposeBeerCellarTheme
import com.example.composebeercellar.views.AuthenticationView
import com.example.composebeercellar.views.BeerAddView
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
    val authViewModel: AuthenticationViewModel = viewModel()
    val viewModel: BeersViewModel = viewModel()
    val isUserLoggedIn = authViewModel.user != null
    val beers = viewModel.beersFlow.value
    val errorMessage = viewModel.errorMessageFlow.value

    val currentUserEmail = authViewModel.user?.email

    val userBeers = beers.filter { it.user == currentUserEmail }

    val logoutUser: () -> Unit = {
        authViewModel.signOut() // Call the signOut function in the ViewModel
        navController.navigate(NavRoutes.Authentication.route) {
            popUpTo(NavRoutes.BeerList.route) { inclusive = true }
        }
    }


    NavHost(navController = navController, startDestination = if (isUserLoggedIn) NavRoutes.BeerList.route else NavRoutes.Authentication.route) {
        composable(NavRoutes.Authentication.route) {
            AuthenticationView(
                user = authViewModel.user,
                message = authViewModel.message,
                signIn = { email, password -> authViewModel.signIn(email, password) },
                register = { email, password -> authViewModel.register(email, password) },

                navigateToNextScreen = {
                    navController.navigate(NavRoutes.BeerList.route) {
                        popUpTo(NavRoutes.Authentication.route) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoutes.BeerList.route) {
            BeerListView(
                modifier = modifier,
                beers = userBeers,
                errorMessage = errorMessage,
                onBeerSelected =
                { beer -> navController.navigate(NavRoutes.BeerDetails.route + "/${beer.id}") },
                onBeerDeleted = { beer -> viewModel.delete(beer) },
                onAdd = { navController.navigate(NavRoutes.BeerAdd.route) },
                sortByName = { viewModel.sortBeersByName(ascending = it) },
                sortByABV = { viewModel.sortBeersByABV(ascending = it) },
                filterByName = { viewModel.filterByName(it) },
                onLogout = logoutUser
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
                onUpdate = { id, updatedBeer ->
                    viewModel.update(id, updatedBeer)
                },
                onBack = { navController.popBackStack() })
        }
        composable(NavRoutes.BeerAdd.route) {
            BeerAddView(
                onAddBeer = { beer -> viewModel.add(beer) },
                onBack = { navController.popBackStack() },
                currentUser = currentUserEmail ?: "No specified User"
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun BeerListPreview() {
    ComposeBeerCellarTheme {
        MainScreen()
    }
}
