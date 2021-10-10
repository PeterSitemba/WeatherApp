package faba.app.weatherapp.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import faba.app.weatherapp.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeatherData: CurrentWeatherData)

    @Query("SELECT * FROM current_weather_table ORDER BY id ASC")
    fun getAllCurrentWeatherData(): Flow<List<CurrentWeatherData>>

    @Query("DELETE FROM current_weather_table")
    suspend fun deleteAll()

}