package com.example.mytrashyapp.di

import android.content.Context
import com.example.mytrashyapp.data.local.preferences.UserPreferences
import com.example.mytrashyapp.data.remote.AuthApi
import com.example.mytrashyapp.data.remote.MusicApi
import com.example.mytrashyapp.data.remote.RemoteDataSource
import com.example.mytrashyapp.data.repository.AuthRepository
import com.example.mytrashyapp.util.Constants.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideAuthApi(
            remoteDataSource: RemoteDataSource,
    ): AuthApi {
        return remoteDataSource.buildApi(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMusicApi(
        remoteDataSource: RemoteDataSource,
    ): MusicApi {
        return remoteDataSource.buildApi(MusicApi::class.java)
    }


    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext appContext: Context) = UserPreferences(appContext)

}