package faba.app.weatherapp.service

import faba.app.weatherapp.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val URL = "https://api.openweathermap.org/data/2.5/weather/"

interface RetrofitService {

    @GET(URL)
    suspend fun getCurrentWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String
    ): Response<WeatherData>

}