package faba.app.weatherapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import faba.app.weatherapp.models.forecast.ForecastData

@Entity(tableName = "weather_forecast_table")
data class ForecastWeatherData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "forecast_data") @TypeConverters(Converter::class) val forecastData: ForecastData
)
