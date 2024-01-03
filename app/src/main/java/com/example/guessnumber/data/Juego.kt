package com.example.guessnumber.data

import android.os.Parcel
import android.os.Parcelable
import kotlin.random.Random

data class Juego(val nombre: String, var nIntentos: Int) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeInt(nIntentos)
    }

    override fun describeContents(): Int {
        return 0
    }

    val numeroAleatorio = (Math.random() * 100 + 1).toInt()

    var contador : Int = 0

    var acertado = false

    companion object CREATOR : Parcelable.Creator<Juego> {

        val TAG = "Juego"

        override fun createFromParcel(parcel: Parcel): Juego {
            return Juego(parcel)
        }

        override fun newArray(size: Int): Array<Juego?> {
            return arrayOfNulls(size)
        }
    }
}
