package com.example.munchiescompose.ui.detail

sealed interface DetailScreenNavigation {
    data object None : DetailScreenNavigation
    data object MainScreen: DetailScreenNavigation
}