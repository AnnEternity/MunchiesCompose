package com.example.munchiescompose.network

data class FiltersAndRestaurants(
    val restaurants: List<RestaurantsData>,
    val filters: List<FilterResponse>
)
