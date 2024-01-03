package com.example.guessnumber.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigViewModel : ViewModel() {

    var nombre = MutableLiveData<String>()
    var numero = MutableLiveData<String>()

    private var state = MutableLiveData<ConfigState>()

    fun getState(): LiveData<ConfigState> {
        return state
    }

    fun validarNombreYNumero() {
        when {
            TextUtils.isEmpty(nombre.value) -> state.value = ConfigState.EmptyName
            TextUtils.isEmpty(numero.value) -> state.value = ConfigState.EmptyNumber
            !validarNumero(numero.value!!) -> state.value = ConfigState.NumberFormatError

            else -> {
                state.value = ConfigState.Success
            }
        }
    }

    private fun validarNumero(edad: String): Boolean {
        try {
            val intEdad = edad.toInt()
            return intEdad > 0
        } catch (e: NumberFormatException) {
            return false
        }
    }

}