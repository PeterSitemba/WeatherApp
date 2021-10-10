package faba.app.weatherapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import faba.app.weatherapp.model.WeatherData

@Entity(tableName = "current_weather_table")
data class CurrentWeatherData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "weatherData") @TypeConverters(Converter::class) val weatherData: WeatherData
)