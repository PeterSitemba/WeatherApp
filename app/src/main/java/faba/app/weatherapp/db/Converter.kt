package faba.app.weatherapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import faba.app.weatherapp.model.WeatherData

class Converter {

    @TypeConverter
    fun toWeatherData(json: String): WeatherData {
        val type = object : TypeToken<WeatherData>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(events: WeatherData): String {
        val type = object : TypeToken<WeatherData>() {}.type
        return Gson().toJson(events, type)
    }

}