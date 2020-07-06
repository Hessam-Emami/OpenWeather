package com.emami.openweather.forecast.data.local

import com.emami.openweather.forecast.data.local.model.CityLocation

interface LocalDataSource {
    fun fetchAvailableCities(): List<CityLocation>
}