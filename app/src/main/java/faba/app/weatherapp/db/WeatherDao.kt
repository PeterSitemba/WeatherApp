package faba.app.weatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    //current
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeatherData: CurrentWeatherData)

    @Query("SELECT * FROM current_weather_table")
    fun getAllCurrentWeatherData(): Flow<List<CurrentWeatherData>>  //remove list, change to CurrentWeatherData obj

    @Query("DELETE FROM current_weather_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(id) FROM current_weather_table")
    fun getRowCount(): Flow<Int?>?


    //forecast
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastWeather(forecastWeatherData: ForecastWeatherData)

    @Query("SELECT * FROM weather_forecast_table")
    fun getAllWeatherForecast(): Flow<List<ForecastWeatherData>> //remove list, change to ForecastWeatherData obj

    @Query("DELETE FROM weather_forecast_table")
    suspend fun deleteAllForecast()

    @Query("SELECT COUNT(id) FROM weather_forecast_table")
    fun getForecastRowCount(): Flow<Int?>?

}