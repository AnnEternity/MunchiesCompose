package com.example.munchiescompose.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://food-delivery.umain.io"

interface ApiService {

    @GET("/api/v1/filter/{id}")
    suspend fun getFilter(
        @Path("id") filterId: String
    ): FilterResponse

    @GET("/api/v1/restaurants")
    suspend fun getAllRestaurants(
    ): RestaurantResponse

    @GET("/api/v1/open/{id}")
    suspend fun getOpenStatus(
        @Path("id") restaurantId: String
    ): RestaurantOpenStatusResponse
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object FoodDeliveryApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}