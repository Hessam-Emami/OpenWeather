package com.emami.openweather.forecast.di

import com.emami.openweather.app.di.scope.ActivityScope
import com.emami.openweather.app.ui.MainActivity
import com.emami.openweather.forecast.di.module.WeatherModule
import com.emami.openweather.forecast.di.module.WeatherNetworkModule
import dagger.Subcomponent

@Subcomponent(modules = [WeatherModule::class, WeatherNetworkModule::class])
@ActivityScope
interface WeatherComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WeatherComponent
    }

    fun inject(activity: MainActivity)
}

