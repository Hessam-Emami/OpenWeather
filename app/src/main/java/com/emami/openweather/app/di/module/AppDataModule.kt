package com.emami.openweather.app.di.module

import androidx.lifecycle.ViewModelProvider
import com.emami.openweather.app.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Provides persistence related objects such as database, shared pref, etc.
 */
@Module
abstract class AppDataModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}