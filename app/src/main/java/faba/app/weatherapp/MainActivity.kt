package faba.app.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import faba.app.weatherapp.db.CurrentWeatherData
import faba.app.weatherapp.ui.theme.WeatherAppTheme
import faba.app.weatherapp.viewmodel.WeatherViewModel
import androidx.compose.runtime.livedata.observeAsState
import faba.app.weatherapp.db.ForecastWeatherData
import faba.app.weatherapp.uicomponents.WeatherScreen
import java.io.IOException
import java.lang.Exception


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            WeatherAppTheme {
                WeatherActivityScreen(weatherViewModel)
            }

            initCurrentWeather()
            initWeatherForecast()


            weatherViewModel.errorMessage.observe(this, {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

        }
    }

    private fun initCurrentWeather() {
        weatherViewModel.getCurrentWeather(
            -1.286322,
            36.78309,
            "2a5ac244383461b7c2225b066ef65029"
        )

        weatherViewModel.getRowCount()?.observe(this, {
            //Log.e("No of rows", it.toString())
        })

        weatherViewModel.currentWeatherResponse.observe(this, {
            weatherViewModel.insertCurrentWeatherData(
                CurrentWeatherData(
                    1,
                    it
                )
            )

        })
    }

    private fun initWeatherForecast() {
        weatherViewModel.getWeatherForecast(
            -1.286322,
            36.78309,
            "2a5ac244383461b7c2225b066ef65029"
        )

        weatherViewModel.getForecastRowCount()?.observe(this, {
            //Log.e("No of rows", it.toString())
        })

        weatherViewModel.weatherForecastResponse.observe(this, {
            weatherViewModel.insertForecast(
                ForecastWeatherData(
                    1,
                    it
                )
            )

        })
    }

}

@Composable
fun WeatherActivityScreen(weatherViewModel: WeatherViewModel) {

    val weatherItems: List<CurrentWeatherData> by weatherViewModel.roomCurrentWeatherData()
        .observeAsState(listOf())

    val weatherForecastItems: List<ForecastWeatherData> by weatherViewModel.roomWeatherForecastData()
        .observeAsState(listOf())

    WeatherScreen(weatherItems, weatherForecastItems)


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        //WeatherScreen("sunny")
    }
}