package com.pablogonzalezpatarro.superheroapi.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    val lg: String,
    val md: String,
    val sm: String,
    val xs: String
):Parcelable