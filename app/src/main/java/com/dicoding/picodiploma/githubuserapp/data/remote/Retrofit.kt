package com.dicoding.picodiploma.githubuserapp.data.remote

import com.dicoding.picodiploma.githubuserapp.data.remote.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private const val BASE_URL = "https://api.github.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service: ApiService = retrofit.create(ApiService::class.java)
}