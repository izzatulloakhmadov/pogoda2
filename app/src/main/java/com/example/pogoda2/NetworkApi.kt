package com.example.pogoda2

import com.example.pogoda2.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key")key:String,
        @Query("q")q:String,
        @Query("days")days:String,
    ):Response<Weather>
}