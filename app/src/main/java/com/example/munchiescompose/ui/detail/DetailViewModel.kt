package com.example.munchiescompose.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.munchiescompose.repository.FoodDeliveryRepository
import com.ramcosta.composedestinations.generated.destinations.DetailScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: FoodDeliveryRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = DetailScreenDestination.argsFrom(savedStateHandle)
    private val _viewState = MutableStateFlow(
        DetailViewState(
            restaurant = args.restaurantWithFilters.restaurantEntity,
            filtersList = args.restaurantWithFilters.filtersList,
        )
    )
    val viewState = _viewState.asStateFlow()

    init {
        val restaurantId = args.restaurantWithFilters.restaurantEntity.id
        if (restaurantId != null) {
            viewModelScope.launch {
                val isOpen = repository.getOpenStatus(restaurantId)
                if (isOpen != null) {
                    _viewState.update { oldViewState ->
                        oldViewState.copy(
                            isOpenStatus = isOpen
                        )
                    }
                }
            }
        }
    }

    fun onEvent(detailScreenEvent: DetailScreenEvent) {
        when (detailScreenEvent) {
            is DetailScreenEvent.ChevronIconClicked -> {
                _viewState.update {
                    it.copy(
                        navigation = DetailScreenNavigation.MainScreen,
                    )
                }
            }

            is DetailScreenEvent.NavigationComplete -> {
                _viewState.update {
                    it.copy(
                        navigation = DetailScreenNavigation.None,
                    )
                }
            }
        }
    }

}