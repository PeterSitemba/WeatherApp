package faba.app.weatherapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import faba.app.weatherapp.db.CurrentWeatherData
import faba.app.weatherapp.db.WeatherDao
import faba.app.weatherapp.service.RetrofitService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val retrofitService: RetrofitService,
    private val weatherDao: WeatherDao
) {

    val roomCurrentWeatherList : Flow<List<CurrentWeatherData>> = weatherDao.getAllCurrentWeatherData()

    suspend fun getCurrentWeatherForecast(
        lat: Double,
        lon: Double,
        appId: String
    ) = retrofitService.getCurrentWeatherForecast(
        lat,
        lon,
        appId
    )

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(currentWeatherData: CurrentWeatherData) {
        weatherDao.insertCurrentWeather(currentWeatherData)
    }

    val getRowCount : Flow<Int?>? = weatherDao.getRowCount()
}