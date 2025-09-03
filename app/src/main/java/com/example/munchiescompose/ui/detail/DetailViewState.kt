package com.example.munchiescompose.ui.detail

import com.example.munchiescompose.database.FilterEntity
import com.example.munchiescompose.database.RestaurantEntity

data class DetailViewState (
    val restaurant: RestaurantEntity? = null,
    val filtersList: List<FilterEntity> = emptyList(),
    val isOpenStatus: Boolean = false,
    val navigation: DetailScreenNavigation = DetailScreenNavigation.None,
)