package com.example.munchiescompose.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val id: String? = null,
    val name: String? = null,
    val rating: Float? = null,
    @Json(name = "filterIds") val filterIds: List<String>? = null,
    @Json(name = "image_url") val imageUrl: String? = null,
    @Json(name = "delivery_time_minutes") val deliveryTimeMinutes: Int? = null
) : Parcelable
