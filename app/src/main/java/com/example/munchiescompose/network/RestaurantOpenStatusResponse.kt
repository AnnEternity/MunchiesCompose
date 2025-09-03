package com.example.munchiescompose.network

import com.squareup.moshi.Json

data class RestaurantOpenStatusResponse(
    @Json(name = "restaurant_id") val restaurantId: String? = null,
    @Json(name = "is_currently_open") val isCurrentlyOpen: Boolean? = null
)
