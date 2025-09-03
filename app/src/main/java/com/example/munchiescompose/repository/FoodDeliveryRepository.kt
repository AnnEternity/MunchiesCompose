package com.example.munchiescompose.repository

import android.util.Log
import com.example.fooddelivery.network.RestaurantFilterCrossRef
import com.example.munchiescompose.database.AppDatabase
import com.example.munchiescompose.database.FilterEntity
import com.example.munchiescompose.database.RestaurantEntity
import com.example.munchiescompose.database.RestaurantWithFilters
import com.example.munchiescompose.network.ApiService
import com.example.munchiescompose.network.FilterResponse
import com.example.munchiescompose.network.RestaurantResponse
import kotlinx.coroutines.flow.Flow

private const val TAG = "FoodDeliveryRepository"

class FoodDeliveryRepository(
    val database: AppDatabase,
    val api: ApiService
) {

    private val databaseDao = database.databaseDao()

    suspend fun getFilter(id: String): FilterResponse? {
        val filterResponse = try {
            api.getFilter(filterId = id)
        } catch (e: Exception) {
            Log.e(TAG, "Get filter failed")
            return null
        }
        return filterResponse
    }

    suspend fun getOpenStatus(id: String): Boolean? {
        val status = try {
            api.getOpenStatus(restaurantId = id)
        } catch (e: Exception) {
            Log.e(TAG, "Get open status failed")
            return null
        }
        return status.isCurrentlyOpen
    }

    suspend fun refreshRestaurants() {
        val listRestaurants = try {
            api.getAllRestaurants()
        } catch (e: Exception) {
            Log.e(TAG, "Get restaurants failed")
            return
        }
        val listRestaurantsEntity = listRestaurants.restaurants.mapNotNull {
            it.id?.let { id ->
                RestaurantEntity(
                    id = id,
                    name = it.name,
                    rating = it.rating,
                    imageUrl = it.imageUrl,
                    deliveryTimeMinutes = it.deliveryTimeMinutes
                )
            }
        }
        databaseDao.deleteRestaurants()
        databaseDao.saveRestaurants(*listRestaurantsEntity.toTypedArray())
        val restaurantsEntity = databaseDao.getRestaurantsList()
        val filters = refreshFilters(listRestaurants)
        refreshRestaurantFilterCrossRef(
            listRestaurants,
            restaurantsEntity,
            filters
        )
    }

    suspend fun refreshFilters(listRestaurants: RestaurantResponse): List<FilterEntity> {
        val filterIdsList = mutableListOf<String>()

        for (restaurant in listRestaurants.restaurants) {
            filterIdsList.addAll(restaurant.filterIds ?: emptyList())
        }
        val filterIdsSet = filterIdsList.toSet()
        val filtersMutableList = mutableListOf<FilterResponse>()
        for (filterId in filterIdsSet) {
            val filter = getFilter(filterId)
            if (filter != null) {
                filtersMutableList.add(filter)
            }
        }
        val filters = filtersMutableList.mapNotNull {
            it.id?.let { id -> FilterEntity(id = id, name = it.name, url = it.url) }
        }
        databaseDao.deleteFilters()
        databaseDao.saveFilters(*filters.toTypedArray())
        val filtersList = databaseDao.getFiltersList()
        return filtersList
    }

    suspend fun refreshRestaurantFilterCrossRef(
        listRestaurants: RestaurantResponse,
        listRestaurantEntities: List<RestaurantEntity>,
        listFilters: List<FilterEntity>
    ) {
        val restaurantFilterCrossRef = mutableListOf<RestaurantFilterCrossRef>()
        for (restaurant in listRestaurants.restaurants) {
            val restaurantKey =
                listRestaurantEntities.find { it.id == restaurant.id }?.key ?: continue
            val restaurantFilters = restaurant.filterIds
            if (restaurantFilters != null) {
                for (filter in restaurantFilters) {
                    val filterKey = listFilters.find { it.id == filter }?.key ?: continue
                    val crossRef = RestaurantFilterCrossRef(restaurantKey, filterKey)
                    restaurantFilterCrossRef.add(crossRef)
                }
            }
        }
        databaseDao.deleteRestaurantFilterCrossRef()
        databaseDao.saveRestaurantFilterCrossRef(*restaurantFilterCrossRef.toTypedArray())
    }

    fun getFilters(): Flow<List<FilterEntity>> {
        return databaseDao.getFiltersFlow()
    }

    fun getRestaurantsWithFilters(): Flow<List<RestaurantWithFilters>> {
        return databaseDao.getRestaurantsWithFilters()
    }

}