package com.example.munchiescompose.ui.detail

sealed interface DetailScreenEvent {
    data object ChevronIconClicked : DetailScreenEvent
    data object NavigationComplete : DetailScreenEvent

}