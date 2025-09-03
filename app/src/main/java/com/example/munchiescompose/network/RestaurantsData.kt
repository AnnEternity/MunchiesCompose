package com.example.munchiescompose.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantsData(
    val id: String?,
    val name: String?,
    val rating: Float?,
    val filters: List<FilterResponse>,
    val imageUrl: String?,
    val deliveryTimeMinutes: Int?
) : Parcelable

