package com.example.munchiescompose.ui.main

import com.example.munchiescompose.database.FilterEntity
import com.example.munchiescompose.database.RestaurantWithFilters

data class MainViewState (
    val filterList: List<FilterEntity> = emptyList(),
    val restaurantList: List<RestaurantWithFilters> = emptyList(),
    val clickedFilterKey: List<Int?> = emptyList(),
    val navigation: MainScreenNavigation = MainScreenNavigation.None,
)