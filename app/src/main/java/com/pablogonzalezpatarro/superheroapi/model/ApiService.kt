package com.pablogonzalezpatarro.superheroapi.model

import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    //Método que nos devuelve una lista con todos los resultados que queremos.
    @GET
    suspend fun getAllHeroes(@Url p:String) : Results
}