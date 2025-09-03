package com.example.munchiescompose.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.fooddelivery.network.RestaurantFilterCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {

    @Transaction
    @Query("SELECT * FROM restaurants")
    fun getRestaurantsWithFilters(): Flow<List<RestaurantWithFilters>>

    @Query("SELECT * FROM filters")
    fun getFiltersFlow(): Flow<List<FilterEntity>>

    @Query("SELECT * FROM filters")
    suspend fun getFiltersList(): List<FilterEntity>

    @Query("SELECT * FROM restaurants")
    suspend fun getRestaurantsList(): List<RestaurantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRestaurants(vararg restaurant: RestaurantEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilters(vararg filters: FilterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRestaurantFilterCrossRef(vararg restaurantFilterCrossRef: RestaurantFilterCrossRef)

    @Query("DELETE FROM restaurants")
    suspend fun deleteRestaurants()

    @Query("DELETE FROM filters")
    suspend fun deleteFilters()

    @Query("DELETE FROM filter_restaurant_cross_ref")
    suspend fun deleteRestaurantFilterCrossRef()
}