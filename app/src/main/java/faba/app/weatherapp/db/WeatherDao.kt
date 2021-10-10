package faba.app.weatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeatherData: CurrentWeatherData)

    @Query("SELECT * FROM current_weather_table")
    fun getAllCurrentWeatherData(): Flow<List<CurrentWeatherData>>

    @Query("DELETE FROM current_weather_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(id) FROM current_weather_table")
    fun getRowCount(): Flow<Int?>?

}