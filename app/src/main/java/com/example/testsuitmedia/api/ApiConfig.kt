package com.example.testsuitmedia.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    object RetrofitInstance {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/") // base URL
            .addConverterFactory(GsonConverterFactory.create()) // untuk parsing JSON
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)
    }
}