package faba.app.weatherapp.models.forecast

data class ForecastData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastDays>,
    val message: Int
)