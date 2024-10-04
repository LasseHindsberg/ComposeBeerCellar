package com.example.composebeercellar

sealed class NavRoutes(val route: String) {
    data object BeerList : NavRoutes("list")
    data object BeerDetails : NavRoutes("details")
    data object BeerAdd: NavRoutes("add")
}