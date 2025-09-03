package com.example.munchiescompose.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("restaurants")
@Parcelize
data class RestaurantEntity(
    @PrimaryKey(autoGenerate = true) val key: Int = 0,
    val id: String?,
    val name: String?,
    val rating: Float?,
    val imageUrl: String?,
    val deliveryTimeMinutes: Int?
) : Parcelable