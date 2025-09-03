package com.example.munchiescompose.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterResponse (
    val id: String? = null,
    val name: String? = null,
    @Json (name = "image_url") val url: String? = null
):Parcelable