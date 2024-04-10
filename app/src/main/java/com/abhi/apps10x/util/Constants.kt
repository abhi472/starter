package com.abhi.apps10x.util

import android.annotation.SuppressLint
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val APP_ID = "9b8cb8c7f11c077f8c4e217974d9ee40"
    const val PERIODIC_SYNC_INTERVAL = 30 * 60 * 1000
}


@SuppressLint("SimpleDateFormat")
fun getAbbreviatedFromDateTime(dateInMilliseconds: Long,
                               dateFormat: String = "yyyy-MM-dd hh:mm:ss",
                               field: String = "EEE", temp: Double?): Pair<String, String> {
    val output = SimpleDateFormat(field)

    try {
        return if (temp != null) {
            Pair(convertDate(dateInMilliseconds, field), String.format("%.0f", temp - 273.15))
        } else Pair(convertDate(dateInMilliseconds, field), "")
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return Pair("", "")
}

fun convertDate(dateInMilliseconds: Long, dateFormat: String?): String {
    return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
}

fun <T> LifecycleOwner.safeCollectFlow(flow: Flow<T>, result: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                result.invoke(it)
            }
        }
    }
}


