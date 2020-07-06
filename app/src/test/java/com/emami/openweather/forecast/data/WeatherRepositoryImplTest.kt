package com.emami.openweather.forecast.data

import com.emami.openweather.forecast.data.local.LocalDataSource
import com.emami.openweather.forecast.data.network.WeatherApi
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class WeatherRepositoryImplTest {


    private val api = mock<WeatherApi>()
    private val localDataSource = mock<LocalDataSource>()
    private val repo = WeatherRepositoryImpl(api, localDataSource)

    @Test
    fun `should verify localDataSource gets called with getAvailableCities`() {
        repo.getAvailableCities()
        verify(localDataSource).fetchAvailableCities()
    }

}