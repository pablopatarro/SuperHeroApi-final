package com.pablogonzalezpatarro.superheroapi.model


import androidx.viewbinding.BuildConfig
import com.pablogonzalezpatarro.superheroapi.Hero
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RemoteConnection {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
        .build()

    //Construimos una instancia de retrofit. Enviamos la url base.
    private val builder: Retrofit = Retrofit.Builder()
        .baseUrl("https://akabab.github.io/superhero-api/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Guardamos el ApiService en una variable que usaremos desde MainViewModel
     val results: ApiService = builder.create()
}