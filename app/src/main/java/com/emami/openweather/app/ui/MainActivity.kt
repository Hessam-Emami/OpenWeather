package com.emami.openweather.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emami.openweather.R
import com.emami.openweather.app.OpenWeatherApp
import com.emami.openweather.app.di.factory.OpenWeatherFragmentFactory
import com.emami.openweather.forecast.ui.weather.WeatherFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainNavigator {

    @Inject
    lateinit var fragmentFactory: OpenWeatherFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as OpenWeatherApp).appComponent.weatherComponent().create().also {
            it.inject(this)
        }
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateToWeatherScreen()
        }
    }

    override fun navigateToWeatherScreen() {
        supportFragmentManager.beginTransaction()
            .replace(
                frameContainer.id,
                WeatherFragment::class.java, null
            )
            .commit()
    }
}

