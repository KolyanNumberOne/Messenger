/*
package com.example.homeproject.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.104:8080/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Конвертер для работы с JSON
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}*/
