package com.abhi.apps10x.data.network

import com.google.gson.JsonNull
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter


data class ExchangeRates(
    @SerializedName("disclaimer")
    val disclaimer: String,
    @SerializedName("license")
    val license: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    @JsonAdapter(JsonObjectToListMapConverter::class) // Custom adapter for Map of rates
    val rates: Map<String, Double>
)

class JsonObjectToListMapConverter : TypeAdapter<Map<String, Double>>() {

    override fun read(reader: JsonReader): Map<String, Double> {
        val jsonObject = JsonParser.parseReader(reader).asJsonObject
        val map: MutableMap<String, Double> =  mutableMapOf()

        val iterator = jsonObject.keySet().iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            val value: String = when (val element = jsonObject.get(key)) {
                is JsonNull -> "" // Handle null values as empty strings
                else -> element.asString
            }
            map.put(key, value.toDouble())
        }
        return map
    }

    override fun write(out: JsonWriter?, value: Map<String, Double>?) {
        TODO("Not yet implemented")
    }
}