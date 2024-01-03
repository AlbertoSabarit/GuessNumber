package com.example.guessnumber.usecase

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayViewModel : ViewModel() {

    var numero = MutableLiveData<String>()

    private var state = MutableLiveData<PlayState>()

    fun getState(): LiveData<PlayState> {
        return state
    }

    fun validarNumeroAcertado() {
        when {
            TextUtils.isEmpty(numero.value) -> state.value = PlayState.EmptyNumber
            !validarNumero(numero.value!!) -> state.value = PlayState.NumberFormatError

            else -> state.value = PlayState.Success
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