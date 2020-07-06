package com.emami.openweather.forecast.ui.model

import androidx.annotation.DrawableRes

data class UiHourlyWeather(
    val temperature: Int,
    val hourOfDay: String,
    @DrawableRes val drawableResourceId: Int
)