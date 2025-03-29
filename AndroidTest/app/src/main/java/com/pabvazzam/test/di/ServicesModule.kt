package com.pabvazzam.test.di

import android.content.SharedPreferences
import com.google.gson.Gson
import com.pabvazzam.test.BASE_URL
import com.pabvazzam.test.api.ApiService
import com.pabvazzam.test.repository.CharacterRepositoryImpl
import com.pabvazzam.test.repository.SharedPreferencesRepositoryImpl
import com.pabvazzam.test.services.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemServiceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        sharedPreferencesManager: SharedPreferencesManager
    ): SharedPreferences {
        return sharedPreferencesManager.getSharedPreferences()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): SharedPreferencesRepositoryImpl {
        return SharedPreferencesRepositoryImpl(sharedPreferences, gson)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(
        sharedPreferences: SharedPreferences,
        gson: Gson,
        apiService: ApiService
    ): CharacterRepositoryImpl {
        return CharacterRepositoryImpl(sharedPreferences, gson, apiService)
    }

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl: String): ApiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}