package com.example.kotlin_mvvm_retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("android")
    fun getAndroid(): Call<List<Android>>
}





