package com.example.munchiescompose.ui.main

import com.example.munchiescompose.database.RestaurantWithFilters

sealed interface MainScreenNavigation {

    data object None : MainScreenNavigation

    data class DetailScreen(val restaurantWithFilters: RestaurantWithFilters) : MainScreenNavigation
}