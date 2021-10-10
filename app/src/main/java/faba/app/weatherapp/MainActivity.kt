package faba.app.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import faba.app.weatherapp.ui.theme.WeatherAppTheme
import faba.app.weatherapp.viewmodel.WeatherViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            weatherViewModel.getCurrentWeatherForecast(
                -1.278319,
                36.784644,
                "2a5ac244383461b7c2225b066ef65029"
            )

            weatherViewModel.weatherForecastResponse.observe(this, {
                Log.e("Weather ", it.base)
            })

/*
            WeatherAppTheme {
                WeatherScreen("sunny")
            }
*/
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        WeatherScreen("sunny")
    }
}