package com.example.amit.applaunchdemo.retrofit

import com.example.amit.applaunchdemo.model.WeatherModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("onecall?lat=12.9082847623315&lon=77.65197822993314&units=imperial&appid=b143bb707b2ee117e62649b358207d3e")
    suspend fun getAllWeatherData(): Response<WeatherModel>

    companion object {

        var retrofitService: RetrofitService? = null

      //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}