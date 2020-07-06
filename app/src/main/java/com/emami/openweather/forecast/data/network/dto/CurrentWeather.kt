package com.emami.openweather.forecast.data.network.dto

import com.emami.openweather.common.util.toLocalDateTime
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class CurrentWeather(
    @SerializedName("dt") val unixTime: Long,
    @SerializedName("temp") val temperature: Double,
    val weather: List<WeatherState>
) {
    val instantTime: LocalDateTime
        get() = unixTime.toLocalDateTime()
}

