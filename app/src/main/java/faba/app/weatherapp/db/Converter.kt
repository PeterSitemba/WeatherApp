package faba.app.weatherapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import faba.app.weatherapp.models.current.WeatherData
import faba.app.weatherapp.models.forecast.ForecastData

class Converter {

    @TypeConverter
    fun toWeatherData(json: String): WeatherData {
        val type = object : TypeToken<WeatherData>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(weatherData: WeatherData): String {
        val type = object : TypeToken<WeatherData>() {}.type
        return Gson().toJson(weatherData, type)
    }

    @TypeConverter
    fun toWeatherForecastData(json: String): ForecastData {
        val type = object : TypeToken<ForecastData>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJsonForecast(forecastData: ForecastData): String {
        val type = object : TypeToken<ForecastData>() {}.type
        return Gson().toJson(forecastData, type)
    }
}