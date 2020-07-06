package com.emami.openweather.forecast.data.network.dto

data class DailyTemperatures(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)