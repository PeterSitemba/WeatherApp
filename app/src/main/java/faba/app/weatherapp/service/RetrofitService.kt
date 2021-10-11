package faba.app.weatherapp.service

import faba.app.weatherapp.models.current.WeatherData
import faba.app.weatherapp.models.forecast.ForecastData
import faba.app.weatherapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET(Constants.CURRENT_URL)
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Response<WeatherData>

    @GET(Constants.FORECAST_URL)
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String,
    ): Response<ForecastData>


}