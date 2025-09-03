package com.example.munchiescompose.database

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.fooddelivery.network.RestaurantFilterCrossRef
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantWithFilters(
    @Embedded val restaurantEntity: RestaurantEntity,
    @Relation(
        parentColumn = "key",
        entityColumn = "key",
        associateBy = Junction(
            value = RestaurantFilterCrossRef::class,
            parentColumn = "restaurantKey",
            entityColumn = "filterKey"
        )
    )
    val filtersList: List<FilterEntity>
) : Parcelable
