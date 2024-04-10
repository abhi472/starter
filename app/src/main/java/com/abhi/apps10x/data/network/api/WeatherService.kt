package com.abhi.apps10x.data.network.api

import com.abhi.apps10x.data.Forecast
import com.abhi.apps10x.data.Weather
import com.abhi.apps10x.data.WeatherResponse
import com.abhi.apps10x.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface WeatherService {

    @GET("forecast")
    suspend fun getForecast(@Query("q") q: String = "Bengaluru",
                            @Query("APPID") id: String = Constants.APP_ID): Forecast

    @GET("weather")
    suspend fun getWeather(@Query("q") q: String = "Bengaluru",
                            @Query("APPID") id: String = Constants.APP_ID): WeatherResponse
}

class WeatherApi @Inject constructor(
    private val weatherService: WeatherService
) {

     suspend fun getWeather() = weatherService.getWeather()
     suspend fun getForecast() = weatherService.getForecast()

}