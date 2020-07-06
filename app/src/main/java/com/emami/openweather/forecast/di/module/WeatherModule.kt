package com.emami.openweather.forecast.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.emami.openweather.app.di.key.FragmentKey
import com.emami.openweather.app.di.key.ViewModelKey
import com.emami.openweather.forecast.data.WeatherRepository
import com.emami.openweather.forecast.data.WeatherRepositoryImpl
import com.emami.openweather.forecast.data.local.DummyLocalDataSource
import com.emami.openweather.forecast.data.local.LocalDataSource
import com.emami.openweather.forecast.ui.weather.WeatherFragment
import com.emami.openweather.forecast.ui.weather.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WeatherModule {
    /**
     * Binds this viewModel into a map of viewModels to later be used by [ViewModelFactory]
     */
    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(weatherViewModel: WeatherViewModel): ViewModel

    /**
     * Binds this fragment into a map of fragments to later be used by [FragmentFactory]
     */
    @Binds
    @IntoMap
    @FragmentKey(WeatherFragment::class)
    abstract fun bindWeatherFragment(weatherFragment: WeatherFragment): Fragment

    @Binds
    abstract fun bindLocalDataSource(dummyLocalDataSource: DummyLocalDataSource): LocalDataSource

    @Binds
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}