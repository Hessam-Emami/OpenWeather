package com.emami.openweather.app.di.module

import com.emami.openweather.forecast.di.WeatherComponent
import dagger.Module

@Module(subcomponents = [WeatherComponent::class])
object SubComponentModule