package com.emami.openweather.app.di.module

//import com.emami.openweather.core.persistence.db.AppDatabase
import dagger.Module

/**
 * Provides persistence related objects such as database, shared pref, etc.
 */
@Module
abstract class AppDataModule {
//
//    @Singleton
//    @Provides
//    fun provideAppDatabase(appContext: Context): AppDatabase = Room.databaseBuilder(
//        appContext, AppDatabase::class.java,
//        AppDatabase.DB_NAME
//    ).fallbackToDestructiveMigration().build()


}