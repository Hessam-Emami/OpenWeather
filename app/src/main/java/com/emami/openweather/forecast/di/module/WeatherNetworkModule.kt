package com.emami.openweather.forecast.di.module

import com.emami.openweather.app.di.scope.ActivityScope
import com.emami.openweather.forecast.data.network.WeatherApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object WeatherNetworkModule {

    @Provides
    @ActivityScope
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(
        WeatherApi::class.java
    )
}