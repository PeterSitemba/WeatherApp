package faba.app.weatherapp.models.forecast

import faba.app.weatherapp.models.current.Weather

data class ForecastDays(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)