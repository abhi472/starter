package com.abhi.apps10x.di


import com.abhi.apps10x.data.network.api.WeatherApi
import com.abhi.apps10x.repository.MainRepository
import com.abhi.apps10x.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCountryDataRepository(weatherApi: WeatherApi): MainRepository {
        return MainRepositoryImpl(weatherApi)
    }
}