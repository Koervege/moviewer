package com.carce.moviewer.networkService

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val gson = GsonBuilder().setLenient().create()
    val retrofit: RetrofitServices by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ApiURL.BASE_URL)
            .build().create(RetrofitServices::class.java)
    }
}
