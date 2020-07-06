package com.emami.openweather.app

import android.app.Application
import com.emami.openweather.app.di.AppComponent
import com.emami.openweather.app.di.DaggerAppComponent
import timber.log.Timber

class OpenWeatherApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder().application(this).build()
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}

