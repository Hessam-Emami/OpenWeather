package com.emami.openweather.forecast.data

import com.emami.openweather.app.di.scope.ActivityScope
import com.emami.openweather.common.base.BaseRepository
import com.emami.openweather.common.util.DataResult
import com.emami.openweather.forecast.data.local.LocalDataSource
import com.emami.openweather.forecast.data.network.WeatherApi
import com.emami.openweather.forecast.data.network.dto.OpenWeatherResponse
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val localDataSource: LocalDataSource
) :
    BaseRepository(), WeatherRepository {

    override fun getAvailableCities() = localDataSource.fetchAvailableCities()

    override suspend fun fetchWeatherForecast(cityId: Int): DataResult<OpenWeatherResponse> =
        invokeApi {
            val city = getAvailableCities().find { it.id == cityId }
            if (city == null) {
                Timber.e("City not found")
                throw RuntimeException("City not found, Data is being manipulated")
            }
            weatherApi.getWeather(latitude = city.latitude, longitude = city.longitude)
        }
}