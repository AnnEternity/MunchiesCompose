package com.example.munchiescompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchiescompose.database.RestaurantWithFilters
import com.example.munchiescompose.repository.FoodDeliveryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: FoodDeliveryRepository) : ViewModel() {
    private val _viewState = MutableStateFlow(MainViewState())
    val viewState = _viewState.asStateFlow()

    private var fullRestaurantsList: List<RestaurantWithFilters> = emptyList()

    init {
        viewModelScope.launch {
            repository.refreshRestaurants()
        }
        updateFilters()
        updateRestaurants()
    }

    private fun updateRestaurants() {
        viewModelScope.launch {
            repository.getRestaurantsWithFilters().collect { it ->
                fullRestaurantsList = it
                _viewState.update { oldViewState ->
                    oldViewState.copy(
                        restaurantList = it
                    )
                }
            }
        }
    }

    private fun updateFilters() {
        viewModelScope.launch {
            repository.getFilters().collect { it ->
                _viewState.update { oldViewState ->
                    oldViewState.copy(
                        filterList = it
                    )
                }
            }
        }
    }

    fun onEvent(mainScreenEvent: MainScreenEvent) {
        when (mainScreenEvent) {
            is MainScreenEvent.FilterClicked -> _viewState.update { it ->
                val newFiltersList = if (it.clickedFilterKey.contains(mainScreenEvent.filter.key)) {
                    it.clickedFilterKey - mainScreenEvent.filter.key
                } else {
                    it.clickedFilterKey + mainScreenEvent.filter.key
                }
                val newRestaurantsList: MutableList<RestaurantWithFilters> = mutableListOf()
                if (newFiltersList.isEmpty()) {
                    for (restaurants in fullRestaurantsList) {
                        newRestaurantsList.add(restaurants)
                    }
                } else {
                    for (restaurant in fullRestaurantsList) {
                        if (restaurant.filtersList.any { newFiltersList.contains(it.key) }) {
                            newRestaurantsList.add(restaurant)
                        }
                    }
                }

                it.copy(
                    clickedFilterKey = newFiltersList,
                    restaurantList = newRestaurantsList,
                )
            }

            is MainScreenEvent.RestaurantCardClicked -> {
                _viewState.update {
                    it.copy(
                        navigation = MainScreenNavigation.DetailScreen(mainScreenEvent.restaurant),
                    )
                }
            }

            is MainScreenEvent.NavigationComplete -> {
                _viewState.update {
                    it.copy(
                        navigation = MainScreenNavigation.None,
                    )
                }
            }
        }
    }
}

