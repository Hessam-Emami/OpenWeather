package com.emami.openweather.forecast.data.network.dto

data class OpenWeatherResponse(
    val hourly: List<CurrentWeather>,
    val daily: List<DailyWeather>
)