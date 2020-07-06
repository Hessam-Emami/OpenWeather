package com.emami.openweather.forecast.data

import com.emami.openweather.common.util.DataResult
import com.emami.openweather.forecast.data.local.model.CityLocation
import com.emami.openweather.forecast.data.network.dto.OpenWeatherResponse

interface WeatherRepository {
    fun getAvailableCities(): List<CityLocation>

    suspend fun fetchWeatherForecast(cityId: Int): DataResult<OpenWeatherResponse>
}