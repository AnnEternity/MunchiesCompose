package com.example.munchiescompose.ui.main

import com.example.munchiescompose.database.FilterEntity
import com.example.munchiescompose.database.RestaurantWithFilters

sealed interface MainScreenEvent {

    data class RestaurantCardClicked(
        val restaurant: RestaurantWithFilters
    ) : MainScreenEvent

    data class FilterClicked(val filter: FilterEntity) : MainScreenEvent

    data class NavigationComplete(val navigation: MainScreenNavigation) : MainScreenEvent
}