package com.sesac.bustame

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val URL = "http://44.194.24.26:8080/"

    var gson = GsonBuilder().setLenient().create()
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val service: RetrofitService =
        retrofit.create(RetrofitService::class.java)


}