package com.emami.openweather.forecast.data.network

import com.emami.openweather.forecast.data.network.dto.OpenWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/onecall")
    suspend fun getWeather(
        @Query("units") unit: String = "metric",
        //Tehran's coordinates
        @Query("lat") latitude: Double = 35.6892,
        @Query("lon") longitude: Double = 51.3890
    ): Response<OpenWeatherResponse>
}

