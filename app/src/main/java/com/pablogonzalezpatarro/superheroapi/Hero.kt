package com.pablogonzalezpatarro.superheroapi

import android.os.Parcelable
import com.pablogonzalezpatarro.superheroapi.data.Images
import com.pablogonzalezpatarro.superheroapi.data.Powerstats
import kotlinx.parcelize.Parcelize

//Hacemos Hero parcelable para poder enviarlo en la navegación.
@Parcelize
data class Hero(
    val id: Int?,
    val nombre:String?,
    val genero:String?,
    val imagen:Images?,
    val estadisticas:Powerstats?):Parcelable{}
