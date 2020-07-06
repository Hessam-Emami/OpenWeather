package com.emami.openweather.forecast.ui.model

import androidx.annotation.DrawableRes

data class UiDailyWeather(
    val dayName: String,
    val minMaxTemperature: UiMinMaxTemperature,
    val temperature: Int,
    @DrawableRes val drawableResourceId: Int
)

