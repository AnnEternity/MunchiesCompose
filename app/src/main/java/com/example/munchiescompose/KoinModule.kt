package com.example.munchiescompose

import com.example.munchiescompose.database.AppDatabase
import com.example.munchiescompose.network.FoodDeliveryApi
import com.example.munchiescompose.repository.FoodDeliveryRepository
import com.example.munchiescompose.ui.detail.DetailViewModel
import com.example.munchiescompose.ui.main.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::DetailViewModel)
    singleOf(AppDatabase::getDatabase)
    singleOf(constructor = {-> FoodDeliveryApi.retrofitService})
    singleOf(::FoodDeliveryRepository)
}