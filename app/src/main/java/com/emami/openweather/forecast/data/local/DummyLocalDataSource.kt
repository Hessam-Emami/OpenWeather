package com.emami.openweather.forecast.data.local

import com.emami.openweather.forecast.data.local.model.CityLocation
import javax.inject.Inject

/**
 * the data is obtained from http://bulk.openweathermap.org/sample/city.list.json.gz
 * This is only for demonstration, later this can be swapped with another implementation
 * of [LocalDataSource] which can read data from Json File, Predefined Room Db or anything else
 */
class DummyLocalDataSource @Inject constructor() : LocalDataSource {
    override fun fetchAvailableCities(): List<CityLocation> = mutableListOf<CityLocation>().apply {
        add(CityLocation(112931, "Tehran", 35.694389, 51.421509))
        add(CityLocation(418863, "Isfahan", 32.657219, 51.677608))
        add(CityLocation(115019, "Shiraz", 29.6036, 52.538799))
        add(CityLocation(124665, "Mashhad", 36.297001, 59.606201))
    }

}