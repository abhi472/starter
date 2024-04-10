package com.abhi.apps10x.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.abhi.apps10x.data.CombineData
import com.abhi.apps10x.repository.MainRepository
import com.abhi.apps10x.states.ApiState
import com.abhi.apps10x.util.Constants
import com.abhi.apps10x.util.getAbbreviatedFromDateTime
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class MainUseCase @Inject constructor(private val repository: MainRepository) {

    suspend fun getExchangeRates() =
        repository.getWeather().combine(flow = repository.getForecast()){ f1, f2 ->
            CombineData(f1, f2, f2.list.map { it -> getAbbreviatedFromDateTime(dateInMilliseconds = it.dt*1000, temp = it.main?.temp) })
        }


}

