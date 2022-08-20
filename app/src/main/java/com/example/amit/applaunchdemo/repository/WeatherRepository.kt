package com.example.amit.applaunchdemo.repository

import com.example.amit.applaunchdemo.retrofit.RetrofitService

class WeatherRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllWeatherData() = retrofitService.getAllWeatherData()

}