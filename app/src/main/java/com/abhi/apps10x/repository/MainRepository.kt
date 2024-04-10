package com.abhi.apps10x.repository

import com.abhi.apps10x.data.Forecast
import com.abhi.apps10x.data.Weather
import com.abhi.apps10x.data.WeatherResponse
import com.abhi.apps10x.data.network.ExchangeRates
import com.abhi.apps10x.data.network.api.WeatherApi
import com.abhi.apps10x.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MainRepository {

    suspend fun getWeather(): Flow<WeatherResponse>
    suspend fun getForecast(): Flow<Forecast>

}

class MainRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi): MainRepository {
    override suspend fun getWeather() =
        flow { emit(weatherApi.getWeather()) }

    override suspend fun getForecast() =
        flow { emit(weatherApi.getForecast()) }


}