package com.example.arch_store.di

import android.content.Context
import android.content.SharedPreferences
import com.example.arch_store.service.ApiCalls
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object SessionModule {

    @Singleton
    @Provides
    fun provideSharePref(@ApplicationContext context: Context): SharedPreferences {
        var PRIVATE_MODE = 0
        val PREF_NAME = "session-data"
        return context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    }

}