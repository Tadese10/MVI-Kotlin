package com.example.androidmvi.api

import com.example.androidmvi.util.LiveDataCallAdapter
import com.example.androidmvi.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofitBuilder {
    val baseUrl = "https://open-api.xyz/"
    val retrofitBuilder : Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
    }

    val apiService : ApiService by lazy {
        retrofitBuilder.build().create(ApiService::class.java)
    }

}