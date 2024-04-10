package com.abhi.apps10x.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi.apps10x.data.Forecast
import com.abhi.apps10x.data.WeatherResponse
import com.abhi.apps10x.states.ApiState
import com.abhi.apps10x.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase,
): ViewModel() {

    private val _search = MutableStateFlow<String>("")
    private var base: Double = 1.0
    private lateinit var rates: Map<String, Double>

    private val _uiState = MutableStateFlow(ApiState())
    val uiState: StateFlow<ApiState> = _uiState.asStateFlow()

    init {
        loadData()

    }

    fun postEvent(event: String) {
        _search.value = event
    }


    private fun loadData() {
        viewModelScope.launch {
            mainUseCase.getExchangeRates()
                .flowOn(Dispatchers.IO)
                .catch {
                    updateUiState(isError = true,
                        isLoading = false,
                    )

                }
                .collect {
                    updateUiState(
                        weatherResponse = it.weatherResponse,
                        forecast = it.forecast,
                        forecastValues = it.forecastValues,
                        isError = false,
                        isLoading = false
                    )


                }
        }
    }

    private fun updateUiState(
        weatherResponse: WeatherResponse = uiState.value.weatherResponse,
        forecast: Forecast = uiState.value.forecast,
        forecastValues: List<Pair<String, String>> = uiState.value.forecastValues,
        isError: Boolean = uiState.value.isError,
        isLoading: Boolean = uiState.value.isLoading,
    ) {
        _uiState.value = uiState.value.copy(
            weatherResponse = weatherResponse,
            forecast = forecast,
            forecastValues = forecastValues,
            isError = isError,
            isLoading = isLoading,
        )
    }

    fun retry() {
        updateUiState(
            isError = false,
            isLoading = true
        )
        loadData()
    }


}