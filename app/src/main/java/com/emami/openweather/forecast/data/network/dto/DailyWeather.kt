package com.emami.openweather.forecast.data.network.dto

import com.emami.openweather.common.util.toLocalDateTime
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class DailyWeather(
    @SerializedName("dt") val unixTime: Long,
    @SerializedName("temp") val temperature: DailyTemperatures,
    val weather: List<WeatherState>
) {
    val instantTime: LocalDateTime
        get() = unixTime.toLocalDateTime()
}
