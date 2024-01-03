package com.example.guessnumber.usecase

sealed class ConfigState() {

    data object EmptyName : ConfigState()
    data object EmptyNumber : ConfigState()
    data object NumberFormatError : ConfigState()
    data object Success : ConfigState()
}