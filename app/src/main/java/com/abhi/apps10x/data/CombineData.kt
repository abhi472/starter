package com.abhi.apps10x.data

import kotlin.collections.List

data class CombineData(

    val weatherResponse: WeatherResponse = WeatherResponse(),
    val forecast: Forecast = Forecast(),
    val forecastValues: List<Pair<String, String>> = emptyList(),
)