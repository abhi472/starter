package com.abhi.apps10x.states

import com.abhi.apps10x.data.Forecast
import com.abhi.apps10x.data.WeatherResponse
import com.abhi.apps10x.data.network.ExchangeRates

data class ApiState (

    val weatherResponse: WeatherResponse = WeatherResponse(),
    val forecast: Forecast = Forecast(),
    val forecastValues: List<Pair<String, String>> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = true,
)