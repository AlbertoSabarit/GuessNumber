package com.example.guessnumber.usecase

sealed class PlayState() {

    data object EmptyNumber : PlayState()
    data object NumberFormatError : PlayState()
    data object Success : PlayState()
}
