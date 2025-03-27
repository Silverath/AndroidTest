package com.pabvazzam.test.di

import android.content.SharedPreferences
import com.pabvazzam.test.services.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemServiceModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(
        sharedPreferencesManager: SharedPreferencesManager
    ): SharedPreferences = sharedPreferencesManager.getSharedPreferences()

}