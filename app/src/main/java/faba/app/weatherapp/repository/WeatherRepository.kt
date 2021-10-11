package faba.app.weatherapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import faba.app.weatherapp.db.CurrentWeatherData
import faba.app.weatherapp.db.ForecastWeatherData
import faba.app.weatherapp.db.WeatherDao
import faba.app.weatherapp.service.RetrofitService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val retrofitService: RetrofitService,
    private val weatherDao: WeatherDao
) {

    val roomCurrentWeatherList : Flow<List<CurrentWeatherData>> = weatherDao.getAllCurrentWeatherData()
    val roomWeatherForecastList : Flow<List<ForecastWeatherData>> = weatherDao.getAllWeatherForecast()


    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        appId: String
    ) = retrofitService.getCurrentWeather(
        lat,
        lon,
        appId,
        units = "metric"
    )

    suspend fun getWeatherForecast(
        lat: Double,
        lon: Double,
        appId: String,
        cnt: Int
    ) = retrofitService.getWeatherForecast(
        lat,
        lon,
        appId,
        cnt,
        units = "metric"
    )


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(currentWeatherData: CurrentWeatherData) {
        weatherDao.insertCurrentWeather(currentWeatherData)
    }

    val getRowCount : Flow<Int?>? = weatherDao.getRowCount()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertForecast(forecastWeatherData: ForecastWeatherData) {
        weatherDao.insertForecastWeather(forecastWeatherData)
    }

    val getForecastRowCount : Flow<Int?>? = weatherDao.getForecastRowCount()
}