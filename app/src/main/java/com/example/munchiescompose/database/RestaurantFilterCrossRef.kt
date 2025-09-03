package com.example.fooddelivery.network

import androidx.room.Entity

@Entity (tableName = "filter_restaurant_cross_ref", primaryKeys = ["restaurantKey", "filterKey"] )
data class RestaurantFilterCrossRef(
    val restaurantKey: Int,
    val filterKey: Int
)
