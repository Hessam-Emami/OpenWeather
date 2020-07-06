package com.emami.openweather.app.di

import android.content.Context
import com.emami.openweather.app.di.module.AppDataModule
import com.emami.openweather.app.di.module.AppNetworkModule
import com.emami.openweather.app.di.module.SubComponentModule
import com.emami.openweather.forecast.di.WeatherComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * graph containing mostly singleton and global shared across modules objects
 */
@Component(
    modules = [AppDataModule::class, AppNetworkModule::class, SubComponentModule::class]
)
@Singleton
interface AppComponent {
    fun weatherComponent(): WeatherComponent.Factory

    @Component.Builder
    interface Builder {
        //Binds the application context to the graph entry
        fun application(@BindsInstance appContext: Context): Builder
        fun build(): AppComponent
    }
}

